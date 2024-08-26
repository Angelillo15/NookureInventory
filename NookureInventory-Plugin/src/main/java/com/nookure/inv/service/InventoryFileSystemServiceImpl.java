package com.nookure.inv.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.inv.api.annotation.PluginInventoriesFolder;
import com.nookure.inv.api.service.InventoryFileSystemService;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Singleton
public class InventoryFileSystemServiceImpl implements InventoryFileSystemService {
  private final Path dataFolder;
  private List<String> inventories;

  @Inject
  public InventoryFileSystemServiceImpl(@PluginInventoriesFolder Path dataFolder) {
    this.dataFolder = dataFolder;
  }

  @Override
  public boolean inventoryExists(@NotNull String inventoryPath) {
    return inventories.contains(inventoryPath);
  }

  @Override
  public void loadInventories() {
    this.inventories = analyzeFolder(dataFolder.toFile());
  }

  @Override
  public List<String> getAvailableInventories() {
    return new ArrayList<>(inventories);
  }

  private List<String> analyzeFolder(@NotNull File folder) {
    List<String> inventories = new ArrayList<>();
    for (File file : requireNonNull(folder.listFiles(), "Inventories must be a folder.")) {
      if (file.isDirectory()) {
        inventories.addAll(analyzeFolder(file));
      } else {
        if (file.getName().toLowerCase().endsWith(".md")) {
          continue;
        }
        inventories.add(file.getPath().replace(dataFolder + File.separator, ""));
      }
    }
    return inventories;
  }
}
