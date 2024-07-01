package com.nookure.core.inv.paper;

import com.nookure.core.inv.I18nAdapter;
import com.nookure.core.inv.exception.UserFriendlyRuntimeException;
import com.nookure.core.inv.parser.GuiLayout;
import com.nookure.core.inv.parser.adapters.MiniMessageAdapter;
import com.nookure.core.inv.parser.item.Action;
import com.nookure.core.inv.parser.item.Item;
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

import static com.nookure.core.inv.paper.ServerUtils.isPaper;
import static java.util.Objects.requireNonNull;

public class InventoryContainer implements InventoryHolder {
  private final Player player;
  private final UUID uuid;
  private final GuiLayout guiLayout;
  private final Inventory inventory;
  private final I18nAdapter i18nAdapter;
  private final PaperOpenInventoryRegistry registry;

  private final Map<String, Item> itemsById = new HashMap<>();

  @SuppressWarnings("deprecation")
  public InventoryContainer(
      @NotNull Player player,
      @NotNull GuiLayout guiLayout,
      @NotNull I18nAdapter i18nAdapter,
      @NotNull PaperOpenInventoryRegistry registry
  ) {
    requireNonNull(player, "Player cannot be null");
    requireNonNull(guiLayout, "GuiLayout cannot be null");
    requireNonNull(i18nAdapter, "I18nAdapter cannot be null");
    requireNonNull(registry, "PaperInventoryRegistry cannot be null");

    this.player = player;
    this.uuid = UUID.randomUUID();
    this.guiLayout = guiLayout;
    this.i18nAdapter = i18nAdapter;
    this.registry = registry;

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
        player.sendPlainMessage("Not implemented yet");
      }
      case SEND_MESSAGE -> {
        if (action.value() == null) {
          throw new UserFriendlyRuntimeException("The SEND_MESSAGE action value cannot be null, please provide a message to send");
        }

        player.sendMessage(i18nAdapter.translateComponent(MiniMessageAdapter.format(action.value()), false));
      }
    }
  }

  @Override
  public org.bukkit.inventory.@NotNull Inventory getInventory() {
    return inventory;
  }

  public @NotNull UUID getUuid() {
    return uuid;
  }
}
