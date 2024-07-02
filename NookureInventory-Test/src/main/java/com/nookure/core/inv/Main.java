package com.nookure.core.inv;

import com.nookure.core.inv.template.extension.PaginationItemExtension;
import com.nookure.core.inv.paper.InventoryContainer;
import com.nookure.core.inv.paper.InventoryListener;
import com.nookure.core.inv.paper.PaperOpenInventoryRegistry;
import com.nookure.core.inv.paper.service.CustomActionRegistry;
import com.nookure.core.inv.parser.GuiLayout;
import jakarta.xml.bind.JAXBException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JavaPlugin implements InventoryOpener<Player> {
  private NookureInventoryEngine engine;
  private static Main instance;
  private I18nAdapter<Player> i18nAdapter;
  private final PaperOpenInventoryRegistry registry = new PaperOpenInventoryRegistry();

  @Override
  public void onEnable() {
    instance = this;
    Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
    i18nAdapter = new I18nMyPluginAdapter();

    try {
      engine = new NookureInventoryEngine(getDataFolder().toPath(), new PaginationItemExtension());
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }

    if (Bukkit.getServicesManager().load(CustomActionRegistry.class) == null) {
      Bukkit.getServicesManager()
          .register(
              CustomActionRegistry.class,
              new CustomActionRegistry(),
              this,
              ServicePriority.Normal
          );
    }

    saveResource("GuiLayoutTest.xml", false);
    saveResource("PaginationTest.peb", true);
    saveResource("PaginationCommon.peb", true);

    CommandMap commandMap = Bukkit.getServer().getCommandMap();

    Bukkit.getPluginManager().registerEvents(new InventoryListener(registry), this);

    commandMap.register("nookure", new Command("test") {
      @Override
      public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (sender instanceof Player player) {
          if (args.length > 0 && args[0].equals("pagination")) {
            List<String> materials = new ArrayList<>();

            Arrays.stream(Material.values()).forEach(material -> materials.add(material.toString()));

            openInventoryAsync(player, "PaginationTest.peb", "materials", materials, "page", 1, "player", player);
          }

          openInventoryAsync(player, "GuiLayoutTest.xml");
        }
        return true;
      }
    });
  }

  private void openInventoryAsync(Player player, String template, Object... args) {
    Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
      GuiLayout guiLayout;

      try {
        if (args.length > 0) {
          guiLayout = engine.parseLayout(template, args);
        } else {
          guiLayout = engine.parseLayout(template, "player", player);
        }
      } catch (JAXBException e) {
        throw new RuntimeException(e);
      }

      Bukkit.getScheduler().runTask(this, () -> {
        player.openInventory(
            new InventoryContainer(
                player,
                guiLayout,
                i18nAdapter,
                registry,
                Main.getInstance(),
                new GuiMetadata(template, args)
            ).getInventory()
        );
      });
    });
  }

  public static Main getInstance() {
    return instance;
  }

  @Override
  public void openAsync(@NotNull Player player, @NotNull String layout, Object... args) {
    openInventoryAsync(player, layout, args);
  }
}