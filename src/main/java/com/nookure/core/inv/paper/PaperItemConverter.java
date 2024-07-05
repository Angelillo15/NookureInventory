package com.nookure.core.inv.paper;

import com.nookure.core.inv.I18nAdapter;
import com.nookure.core.inv.exception.UserFriendlyRuntimeException;
import com.nookure.core.inv.paper.utils.ServerUtils;
import com.nookure.core.inv.paper.utils.SkullCreator;
import com.nookure.core.inv.parser.item.HeadType;
import com.nookure.core.inv.parser.item.Item;
import com.nookure.core.inv.parser.item.ItemConverter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaperItemConverter extends ItemConverter<ItemStack, Player> {
  private static final PaperItemConverter instance = new PaperItemConverter();
  public static final String ID_KEY = "nookureinventory";
  public static final NamespacedKey ITEM_ID = new NamespacedKey(ID_KEY, "item_id");

  public static PaperItemConverter getInstance() {
    return instance;
  }

  @Override
  public Item convert(ItemStack itemStack) {
    return null;
  }

  @Override
  @SuppressWarnings("deprecation")
  public ItemStack convert(Item item, I18nAdapter<Player> adapter) {
    ItemStack itemStack;
    if (item.material() == null) {
      itemStack = createHead(item.head(), item.headType());

      if (itemStack == null) {
        throw new UserFriendlyRuntimeException("Invalid head settings for item " + item.id());
      }
    } else {
      try {
        itemStack = new ItemStack(Material.valueOf(item.material().toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw new UserFriendlyRuntimeException("Invalid material " + item.material() + " for item " + item.id());
      }
    }

    itemStack.setAmount(item.amount());

    ItemMeta meta = itemStack.getItemMeta();

    if (meta == null) {
      return itemStack;
    }

    if (ServerUtils.isPaper) {
      if (item.name() != null) {
        meta.displayName(adapter.translateComponent(item.name(), item.tl()));
      }
      List<Component> lore = new ArrayList<>();

      if (item.lore() != null) {
        if (item.lore().loreLines() != null) {
          item.lore().loreLines().forEach(line -> lore.add(adapter.translateComponent(line, item.tl())));
        }
      }

      if (item.literalLore() != null) {
        if (item.literalLore().loreLines() != null) {
          item.literalLore().loreLines().forEach(line -> lore.add(adapter.translateComponent(line, item.tl())));
        }
      }

      meta.lore(lore);

    } else {
      if (item.name() != null) {
        meta.setDisplayName(adapter.translate(item.name(), item.tl()));
      }

      if (item.lore() != null) {
        List<String> lore = new ArrayList<>();
        if (item.lore().loreLines() != null) {
          item.lore().loreLines().forEach(line -> lore.add(adapter.translate(line, item.tl())));
        }

        meta.setLore(lore);
      }

      if (item.literalLore() != null) {
        List<String> lore = new ArrayList<>();
        item.literalLore().loreLines().forEach(line -> lore.add(adapter.translate(line, item.tl())));

        meta.setLore(lore);
      }
    }

    PersistentDataContainer data = meta.getPersistentDataContainer();

    if (item.id() != null) {
      data.set(ITEM_ID, PersistentDataType.STRING, item.id());
    } else {
      data.set(ITEM_ID, PersistentDataType.STRING, item.material() + "$" + item.slot());
      item.setId(item.material() + "$" + item.slot());
    }

    if (item.enchanted()) {
      meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }

    itemStack.setItemMeta(meta);
    return itemStack;
  }

  private ItemStack createHead(String value, HeadType type) {

    switch (type) {
      case PLAYER:
        // noinspection deprecation
        return SkullCreator.itemFromName(value); // is the best way to manage by name (not recommended)
      case PLAYER_UUID:
        UUID uuid;

        try {
          uuid = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
          throw new UserFriendlyRuntimeException("Invalid UUID " + value + " for item head");
        }

        return SkullCreator.itemFromUuid(uuid);
      case URL:
        return SkullCreator.itemFromUrl(value);
      case BASE64:
        return SkullCreator.itemFromBase64(value);
      default:
        return null;
    }
  }
}
