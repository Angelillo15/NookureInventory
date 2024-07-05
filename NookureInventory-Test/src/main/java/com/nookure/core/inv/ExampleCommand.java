package com.nookure.core.inv;

import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ExampleCommand extends Command {
  private final PaperNookureInventoryEngine engine;

  protected ExampleCommand(@NotNull PaperNookureInventoryEngine engine) {
    super("example");
    this.engine = engine;
  }

  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
    if (sender instanceof Player player) {
      engine.openAsync(player, "our-first-gui.xml");
      return true;
    }

    sender.sendRichMessage("<red>You are the console!");
    return true;
  }
}
