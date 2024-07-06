package com.nookure.core.inv;

import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OpenInventoryTestCommand extends Command {
  private final PaperNookureInventoryEngine engine;
  protected OpenInventoryTestCommand(PaperNookureInventoryEngine engine) {
    super("openinventorytest");
    this.engine = engine;
  }

  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
    if (sender instanceof Player player) {
      engine.openAsync(player, "OpenGui1.peb");
      return true;
    }

    sender.sendRichMessage("<red>You are the console!");
    return true;
  }
}
