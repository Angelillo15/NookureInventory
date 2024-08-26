package com.nookure.inv.command;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.core.logger.Logger;
import com.nookure.inv.api.service.InventoryFileSystemService;
import com.nookure.inv.config.partial.CommandPartial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public class GUICommand extends Command {
  private final AtomicReference<PaperNookureInventoryEngine> engine;
  private final InventoryFileSystemService inventoryFileSystemService;
  private final CommandPartial commandPartial;
  private final Logger logger;
  private final JavaPlugin plugin;

  @Inject
  public GUICommand(
      @NotNull AtomicReference<PaperNookureInventoryEngine> engine,
      @NotNull InventoryFileSystemService inventoryFileSystemService,
      @NotNull Logger logger,
      @NotNull JavaPlugin plugin,
      @NotNull @Assisted CommandPartial command
  ) {
    super(command.command());
    requireNonNull(command, "Command cannot be null.");
    requireNonNull(command.command(), "Command name cannot be null.");
    requireNonNull(command.inventory(), "Inventory cannot be null.");
    requireNonNull(engine, "Engine cannot be null.");
    requireNonNull(inventoryFileSystemService, "Inventory file system service cannot be null.");
    requireNonNull(logger, "Logger cannot be null.");
    requireNonNull(plugin, "Plugin cannot be null.");

    this.engine = engine;
    this.commandPartial = command;
    this.inventoryFileSystemService = inventoryFileSystemService;
    this.logger = logger;
    this.plugin = plugin;

    if (command.permission() != null) {
      this.setPermission(command.permission());
    }

    if (command.description() != null) {
      this.setDescription(command.description());
    }

    if (command.usage() != null) {
      this.setUsage(command.usage());
    }

    if (command.aliases() != null) {
      this.setAliases(command.aliases());
    }

    if (command.inventory() == null) {
      throw new IllegalArgumentException("Inventory cannot be null.");
    }

    if (!inventoryFileSystemService.inventoryExists(command.inventory())) {
      logger.severe("Inventory file does not exist: %s", command.inventory());
    }
  }

  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage("Only players can execute this command.");
      return true;
    }

    if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
      sender.sendMessage("You do not have permission to execute this command.");
      return true;
    }

    if (!inventoryFileSystemService.inventoryExists(commandPartial.inventory())) {
      logger.warning("%s tried to open a non-existent inventory: %s", sender.getName(), commandPartial.inventory());
      sender.sendMessage("This inventory does not exist.");
      return true;
    }

    engine.get().openAsync(player, commandPartial.inventory(), "player", player, "page", 1, "plugin", plugin);
    return true;
  }
}
