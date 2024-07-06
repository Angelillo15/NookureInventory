package com.nookure.core.inv;

import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.core.inv.template.extension.GetBukkitPlayerExtension;
import com.nookure.core.inv.template.extension.OpenInventoryExtension;
import com.nookure.core.inv.template.extension.PaginationItemExtension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JavaPlugin {
  private PaperNookureInventoryEngine engine;

  @Override
  public void onEnable() {
    Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

    saveResource("gui/GuiLayoutTest.xml", false);
    saveResource("gui/PaginationTest.peb", true);
    saveResource("gui/PaginationCommon.peb", true);
    saveResource("gui/PlayerPaginationTest.peb", true);
    saveResource("gui/our-first-gui.xml", false);
    saveResource("gui/OpenGui1.peb", true);
    saveResource("gui/OpenGui2.peb", true);

    engine = new PaperNookureInventoryEngine.Builder()
        .templateFolder("gui")
        .plugin(this)
        .extensions(new PaginationItemExtension(), new OpenInventoryExtension(), new GetBukkitPlayerExtension())
        .build();

    CommandMap commandMap = Bukkit.getServer().getCommandMap();
    commandMap.register("nookure", new ExampleCommand(engine));
    commandMap.register("nookure", new OpenInventoryTestCommand(engine));

    commandMap.register("nookure", new Command("test") {
      @Override
      public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (sender instanceof Player player) {
          if (args.length > 0 && args[0].equals("pagination")) {
            List<String> materials = new ArrayList<>();

            Arrays.stream(Material.values()).forEach(material -> materials.add(material.toString()));

            engine.openAsync(player, "PaginationTest.peb", "materials", materials, "page", 1, "player", player);
            return true;
          }

          if (args.length > 0 && args[0].equals("players")) {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

            engine.openAsync(player, "PlayerPaginationTest.peb", "players", players, "page", 1, "player", player);
            return true;
          }

          engine.openAsync(player, "GuiLayoutTest.xml");
        }
        return true;
      }
    });
  }
}