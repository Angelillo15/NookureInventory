package com.nookure.core.inv.paper;

import com.google.gson.Gson;
import com.nookure.core.inv.GuiMetadata;
import com.nookure.core.inv.I18nAdapter;
import com.nookure.core.inv.InventoryOpener;
import com.nookure.core.inv.NookureInventoryEngine;
import com.nookure.core.inv.exception.UserFriendlyRuntimeException;
import com.nookure.core.inv.paper.service.CustomActionRegistry;
import com.nookure.core.inv.parser.GuiLayout;
import com.nookure.core.inv.parser.adapters.MiniMessageAdapter;
import com.nookure.core.inv.parser.item.Item;
import com.nookure.core.inv.parser.item.action.Action;
import com.nookure.core.inv.parser.item.action.json.OpenInventoryProps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.nookure.core.inv.paper.utils.ServerUtils.isPaper;
import static com.nookure.core.inv.parser.item.action.ActionType.*;
import static java.util.Objects.requireNonNull;

public class InventoryContainer implements InventoryHolder {
  private final Player player;
  private final UUID uuid;
  private final GuiLayout guiLayout;
  private final Inventory inventory;
  private final I18nAdapter<Player> i18nAdapter;
  private final InventoryOpener<Player> opener;
  private final GuiMetadata metadata;
  private final CustomActionRegistry customActionRegistry = Bukkit.getServicesManager().load(CustomActionRegistry.class);
  private final Map<String, Item> itemsById = new HashMap<>();
  private final Gson gson = new Gson();

  @SuppressWarnings("deprecation")
  public InventoryContainer(
      @NotNull Player player,
      @NotNull GuiLayout guiLayout,
      @NotNull I18nAdapter<Player> i18nAdapter,
      @NotNull PaperOpenInventoryRegistry registry,
      @NotNull InventoryOpener<Player> opener,
      @NotNull GuiMetadata metadata
  ) {
    requireNonNull(player, "Player cannot be null");
    requireNonNull(guiLayout, "GuiLayout cannot be null");
    requireNonNull(i18nAdapter, "I18nAdapter cannot be null");
    requireNonNull(registry, "PaperInventoryRegistry cannot be null");

    this.player = player;
    this.uuid = UUID.randomUUID();
    this.guiLayout = guiLayout;
    this.i18nAdapter = i18nAdapter;
    this.opener = opener;
    this.metadata = metadata;

    if (isPaper) {
      this.inventory = Bukkit.createInventory(
          this,
          guiLayout.head().rows() * 9,
          i18nAdapter.translateComponent(guiLayout.head().title().title(), guiLayout.head().title().tl())
      );
    } else {
      this.inventory = Bukkit.createInventory(
          this,
          guiLayout.head().rows() * 9,
          i18nAdapter.translate(guiLayout.head().title().title(), guiLayout.head().title().tl())
      );
    }

    fillItems();
    registry.register(player.getUniqueId(), this);
  }

  public void fillItems() {
    if (guiLayout.items() == null || guiLayout.items().itemList() == null) {
      return;
    }
    guiLayout
        .items()
        .itemList()
        .forEach(item -> {
          inventory.setItem(item.slot(), PaperItemConverter.getInstance().convert(item, i18nAdapter));
          if (item.id() != null) {
            itemsById.put(item.id(), item);
          }
        });
  }

  public void onInventoryClick(@NotNull InventoryClickEvent event) {
    event.setCancelled(true);

    ItemStack item = event.getCurrentItem();

    if (item == null || item.getType().isAir()) {
      return;
    }

    ItemMeta meta = item.getItemMeta();
    PersistentDataContainer container = meta.getPersistentDataContainer();

    if (container.has(PaperItemConverter.ITEM_ID, PersistentDataType.STRING)) {
      String id = container.get(PaperItemConverter.ITEM_ID, PersistentDataType.STRING);
      Item clickedItem = itemsById.get(id);

      if (clickedItem == null) {
        return;
      }

      if (clickedItem.actions() == null) {
        return;
      }

      if (clickedItem.actions().actionsList().isEmpty()) {
        return;
      }

      clickedItem.actions().actionsList().forEach(this::handleClick);
    }
  }

  private void handleClick(Action action) {
    switch (action.type()) {
      case CLOSE_INVENTORY -> player.closeInventory();
      case RUN_COMMAND_AS_CONSOLE -> Bukkit.dispatchCommand(player, action.value());
      case RUN_COMMAND_AS_PLAYER -> {
        if (action.value() == null) {
          throw new UserFriendlyRuntimeException("The RUN_COMMAND_AS_PLAYER action value cannot be null, please provide a command to run");
        }

        player.performCommand(action.value());
      }
      case OPEN_INVENTORY -> {
        player.closeInventory();

        if (action.value() == null) {
          throw new UserFriendlyRuntimeException("The OPEN_INVENTORY action value cannot be null, please provide an inventory to open");
        }

        try {
          OpenInventoryProps props = gson.fromJson(action.value(), OpenInventoryProps.class);
          if (props == null) {
            throw new UserFriendlyRuntimeException("The OPEN_INVENTORY action value is not a valid JSON object");
          }

          if (props.getInventoryName() == null) {
            throw new UserFriendlyRuntimeException("The OPEN_INVENTORY action value does not contain an inventoryName");
          }

          if (props.getContext().isEmpty()) {
            opener.openAsync(player, props.getInventoryName());
            return;
          }


          opener.openAsync(player, props.getInventoryName(), NookureInventoryEngine.toContextObjectArray(props.getContext()));
        } catch (Exception e) {
          throw new UserFriendlyRuntimeException("The OPEN_INVENTORY action value is not a valid JSON object");
        }
      }
      case NEXT_PAGE -> {
        if (!metadata.isPagination()) {
          throw new UserFriendlyRuntimeException("The NEXT_PAGE action can only be used in a paginated inventory");
        }

        changePage(metadata.nextPage());
      }
      case PREVIOUS_PAGE -> {
        if (!metadata.isPagination()) {
          throw new UserFriendlyRuntimeException("The PREVIOUS_PAGE action can only be used in a paginated inventory");
        }

        changePage(metadata.previousPage());
      }
      case SEND_MESSAGE -> {
        if (action.value() == null) {
          throw new UserFriendlyRuntimeException("The SEND_MESSAGE action value cannot be null, please provide a message to send");
        }

        player.sendMessage(i18nAdapter.translateComponent(MiniMessageAdapter.format(action.value()), false));
      }
      default -> {
        if (customActionRegistry == null) {
          return;
        }

        if (customActionRegistry.hasAction(action.type())) {
          CustomPaperAction customPaperAction = customActionRegistry.getAction(action.type());

          if (customPaperAction == null) {
            throw new UserFriendlyRuntimeException("The action " + action.type() + " does not exist");
          }

          if (customPaperAction.getActionData().hasValue() && action.value() == null) {
            throw new UserFriendlyRuntimeException("The action " + action.type() + " requires a value, please provide it");
          }

          customPaperAction.execute(player, action.value());
        } else {
          throw new UserFriendlyRuntimeException("The action " + action.type() + " does not exist");
        }
      }
    }
  }

  private void changePage(int page) {
    Map<String, Object> context = NookureInventoryEngine.toMap(metadata.args());
    context.put("page", page);

    opener.openAsync(player, metadata.layout(), NookureInventoryEngine.toContextObjectArray(context));
  }

  @Override
  public org.bukkit.inventory.@NotNull Inventory getInventory() {
    return inventory;
  }

  public @NotNull UUID getUuid() {
    return uuid;
  }
}
