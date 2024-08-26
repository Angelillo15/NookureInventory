package com.nookure.inv.config;

import com.nookure.core.database.config.DatabaseConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class PaperConfig {
  @Setting
  private DatabaseConfig database = new DatabaseConfig();

  public DatabaseConfig database() {
    return this.database;
  }
}
