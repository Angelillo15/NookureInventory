package com.nookure.core.inv.paper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import javax.inject.Inject;

public class InventoryListener implements Listener {
  private final PaperOpenInventoryRegistry registry;

  @Inject
  public InventoryListener(PaperOpenInventoryRegistry registry) {
    this.registry = registry;
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    InventoryContainer container = registry.get(event.getWhoClicked().getUniqueId());

    if (container != null) {
      container.onInventoryClick(event);
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    InventoryContainer container = registry.get(event.getPlayer().getUniqueId());

    Inventory inventory = event.getInventory();

    if (container == null || !(inventory.getHolder(false) == container)) {
      return;
    }

    registry.unregister(container);
  }
}
