package com.nookure.inv.bootrstrap;

import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.nookure.core.config.ConfigurationContainer;
import com.nookure.core.database.SQLPluginConnection;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.inv.api.annotation.PluginInventoriesFolder;
import com.nookure.inv.api.service.InventoryFileSystemService;
import com.nookure.inv.command.GUICommand;
import com.nookure.inv.config.CommandConfig;
import com.nookure.inv.config.PaperConfig;
import com.nookure.inv.factory.GUICommandFactory;
import com.nookure.inv.service.InventoryFileSystemServiceImpl;
import io.ebean.Database;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public class InventoryModule extends NookCoreModule {
  private final JavaPlugin plugin;

  public InventoryModule(JavaPlugin plugin) {
    super(plugin);
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    super.configure();
    bind(JavaPlugin.class).toInstance(this.plugin);
    bind(Server.class).toInstance(this.plugin.getServer());
    bind(CommandMap.class).toInstance(this.plugin.getServer().getCommandMap());
    bind(Path.class).annotatedWith(PluginInventoriesFolder.class).toInstance(this.plugin.getDataFolder().toPath().resolve("inventories"));
    bind(InventoryFileSystemService.class).to(InventoryFileSystemServiceImpl.class).asEagerSingleton();

    bind(new TypeLiteral<ConfigurationContainer<PaperConfig>>() {
    }).toInstance(loadConfig(PaperConfig.class, "config.yml"));
    bind(new TypeLiteral<ConfigurationContainer<CommandConfig>>() {
    }).toInstance(loadConfig(CommandConfig.class, "commands.yml"));

    bind(new TypeLiteral<AtomicReference<Database>>() {
    }).toInstance(new AtomicReference<>(null));
    bind(new TypeLiteral<AtomicReference<SQLPluginConnection>>() {
    }).toInstance(new AtomicReference<>(null));
    bind(new TypeLiteral<AtomicReference<PaperNookureInventoryEngine>>() {
    }).toInstance(new AtomicReference<>(null));

    install(new FactoryModuleBuilder()
        .implement(GUICommand.class, GUICommand.class)
        .build(GUICommandFactory.class)
    );
  }

  @NotNull
  private <T> ConfigurationContainer<T> loadConfig(@NotNull Class<T> clazz, @NotNull String fileName) {
    try {
      return ConfigurationContainer.load(this.plugin.getDataPath(), clazz, fileName);
    } catch (IOException e) {
      this.plugin.getLogger().severe("Failed to load configuration file: " + fileName);
      this.plugin.getLogger().severe(e.getMessage());
    }

    throw new RuntimeException("Failed to load configuration file: " + fileName);
  }
}
