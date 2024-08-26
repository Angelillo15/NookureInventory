package com.nookure.inv.api.service;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface InventoryFileSystemService {
  /**
   * Check if an inventory exists.
   *
   * @param inventoryPath the path to the inventory.
   * @return true if the inventory exists, false otherwise.
   */
  boolean inventoryExists(@NotNull String inventoryPath);

  /**
   * Load all the inventories.
   */
  void loadInventories();

  /**
   * Get all the inventories paths.
   *
   * @return the file of the inventory, or null if it does not exist.
   */
  List<String> getAvailableInventories();
}
