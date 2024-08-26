package com.nookure.inv;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.core.logger.Logger;
import com.nookure.inv.api.service.InventoryFileSystemService;
import com.nookure.inv.bootrstrap.BootstrapModule;
import com.nookure.inv.bootrstrap.InventoryModule;
import com.nookure.inv.bootrstrap.modules.CommandModule;
import com.nookure.inv.bootrstrap.modules.DatabaseModule;
import com.nookure.inv.bootrstrap.modules.InventoryEngineModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class InventoryPlugin extends JavaPlugin {
  private final List<Class<? extends BootstrapModule>> modules;
  private final ArrayList<BootstrapModule> bootstrapModules = new ArrayList<>();

  {
    modules = List.of(
        DatabaseModule.class,
        InventoryEngineModule.class,
        CommandModule.class
    );
  }

  @Inject
  private Injector injector;
  @Inject
  private Logger logger;
  @Inject
  private InventoryFileSystemService inventoryFileSystemService;

  @Override
  public void onEnable() {
    injector = Guice.createInjector(new InventoryModule(this));
    injector.injectMembers(this);
    logger.debug("NookureInventory is enabling...");
    loadModules();
    logger.debug("NookureInventory has been enabled!");
    logger.debug(inventoryFileSystemService.getAvailableInventories().toString());
  }

  @Override
  public void onDisable() {
    unloadModules();
  }

  public void loadModules() {
    modules.forEach(module -> {
      try {
        BootstrapModule bootstrapModule = injector.getInstance(module);
        bootstrapModules.add(bootstrapModule);
        bootstrapModule.onEnable();
      } catch (Exception e) {
        logger.severe("Failed to load module: " + module.getName());
        logger.severe(e);
      }
    });
  }

  public void reloadModules() {
    bootstrapModules.forEach(e -> {
      try {
        e.onReload();
      } catch (Exception ex) {
        logger.severe("Failed to reload module: " + e.getClass().getName());
        logger.severe(ex);
      }
    });
  }

  public void unloadModules() {
    bootstrapModules.forEach(e -> {
      try {
        e.onDisable();
      } catch (Exception ex) {
        logger.severe("Failed to unload module: " + e.getClass().getName());
        logger.severe(ex);
      }
    });
  }
}
