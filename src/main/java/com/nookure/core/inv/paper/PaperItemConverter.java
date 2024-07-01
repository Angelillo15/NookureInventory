package com.nookure.core.inv.paper;

import com.nookure.core.inv.I18nAdapter;
import com.nookure.core.inv.parser.item.Item;
import com.nookure.core.inv.parser.item.ItemConverter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class PaperItemConverter extends ItemConverter<ItemStack> {
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
  public ItemStack convert(Item item, I18nAdapter adapter) {
    ItemStack itemStack = new ItemStack(Material.valueOf(item.material().toUpperCase()));
    ItemMeta meta = itemStack.getItemMeta();

    if (meta == null) {
      return itemStack;
    }

    if (ServerUtils.isPaper) {
      if (item.name() != null) {
        meta.displayName(adapter.translateComponent(item.name(), item.tl()));
      }

      if (item.lore() != null) {
        List<Component> lore = new ArrayList<>();

        item.lore().loreLines().forEach(line -> lore.add(adapter.translateComponent(line, item.tl())));
        meta.lore(lore);
      }
    } else {
      if (item.name() != null) {
        meta.setDisplayName(adapter.translate(item.name(), item.tl()));
      }

      if (item.lore() != null) {
        List<String> lore = new ArrayList<>();

        item.lore().loreLines().forEach(line -> lore.add(adapter.translate(line, item.tl())));
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
}
