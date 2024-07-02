package com.nookure.core.inv;

import org.jetbrains.annotations.NotNull;

public interface InventoryOpener<T> {
  /**
   * Open an inventory
   *
   * @param player The player to open the inventory for
   * @param layout The layout to open
   * @param args   The arguments to pass to the layout
   */
  void openAsync(@NotNull T player, @NotNull String layout, Object... args);
}
