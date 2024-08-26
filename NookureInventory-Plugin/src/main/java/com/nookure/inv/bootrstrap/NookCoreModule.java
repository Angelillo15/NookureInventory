package com.nookure.inv.bootrstrap;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.nookure.core.annotation.PluginColoredName;
import com.nookure.core.annotation.PluginDataFolder;
import com.nookure.core.annotation.PluginDebug;
import com.nookure.core.annotation.PluginVersion;
import com.nookure.core.database.annotation.EbeanDatabaseName;
import com.nookure.core.database.annotation.EntityClassMappings;
import com.nookure.core.logger.annotation.PluginAudience;
import com.nookure.core.logger.annotation.PluginLoggerColor;
import com.nookure.core.logger.annotation.PluginName;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class NookCoreModule extends AbstractModule {
  private final JavaPlugin plugin;
  private final boolean isDebug;

  public NookCoreModule(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  {
    this.isDebug = Boolean.parseBoolean(System.getProperty("nookure.inventories.debug", "false"));
  }

  @Override
  @SuppressWarnings("UnstableApiUsage")
  protected void configure() {
    bind(String.class).annotatedWith(EbeanDatabaseName.class).toInstance("nkinventory");
    bind(new TypeLiteral<List<Class<?>>>() {
    }).annotatedWith(EntityClassMappings.class).toInstance(List.of());
    bind(Path.class).annotatedWith(PluginDataFolder.class).toInstance(this.plugin.getDataFolder().toPath());
    bind(String.class).annotatedWith(PluginColoredName.class).toInstance("<b><gradient:#E43A96:#F2C6DE>NookureInventory</gradient></b>");
    bind(AtomicBoolean.class).annotatedWith(PluginDebug.class).toInstance(new AtomicBoolean(this.isDebug));
    bind(String.class).annotatedWith(PluginVersion.class).toInstance(this.plugin.getPluginMeta().getVersion());
    bind(Audience.class).annotatedWith(PluginAudience.class).toInstance(Bukkit.getConsoleSender());
    bind(NamedTextColor.class).annotatedWith(PluginColoredName.class).toInstance(NamedTextColor.LIGHT_PURPLE);
    bind(NamedTextColor.class).annotatedWith(PluginLoggerColor.class).toInstance(NamedTextColor.LIGHT_PURPLE);
    bind(String.class).annotatedWith(PluginName.class).toInstance("NookureInventory");
  }
}
