package com.nookure.inv.bootrstrap;

public interface BootstrapModule {
  default void onEnable() {}

  default void onDisable() {}

  default void onReload() {}
}
