package com.nookure.inv.bootrstrap.modules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.core.annotation.PluginDataFolder;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.core.inv.template.extension.PaginationItemExtension;
import com.nookure.core.inv.util.JarUtil;
import com.nookure.core.logger.Logger;
import com.nookure.inv.api.service.InventoryFileSystemService;
import com.nookure.inv.bootrstrap.BootstrapModule;
import com.nookure.inv.extension.ReflectionExtension;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class InventoryEngineModule implements BootstrapModule {
  @Inject
  private AtomicReference<PaperNookureInventoryEngine> engine;
  @Inject
  private JavaPlugin plugin;
  @Inject
  @PluginDataFolder
  private Path dataFolder;
  @Inject
  private Logger logger;
  @Inject
  private InventoryFileSystemService inventoryFileSystemService;

  @Override
  public void onEnable() {
    if (!new File(dataFolder.toFile(), "inventories").exists()) {
      try {
        JarUtil.copyFolderFromJar("inventories", dataFolder.toFile(), JarUtil.CopyOption.COPY_IF_NOT_EXIST);
      } catch (IOException e) {
        logger.severe("Failed to copy inventories folder from jar.");
        logger.severe(e);
      }
    }

    PaperNookureInventoryEngine inventoryEngine = new PaperNookureInventoryEngine.Builder()
        .plugin(plugin)
        .templateFolder("inventories")
        .extensions(new PaginationItemExtension(), new ReflectionExtension())
        .build();

    engine.set(inventoryEngine);

    inventoryFileSystemService.loadInventories();
  }

  @Override
  public void onReload() {
    onEnable();
  }
}
