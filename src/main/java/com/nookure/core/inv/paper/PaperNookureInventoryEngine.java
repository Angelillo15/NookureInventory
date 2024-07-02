package com.nookure.core.inv.paper;

import com.nookure.core.inv.GuiMetadata;
import com.nookure.core.inv.I18nAdapter;
import com.nookure.core.inv.InventoryOpener;
import com.nookure.core.inv.NookureInventoryEngine;
import com.nookure.core.inv.paper.service.CustomActionRegistry;
import com.nookure.core.inv.parser.GuiLayout;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.loader.FileLoader;
import jakarta.xml.bind.JAXBException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PaperNookureInventoryEngine extends NookureInventoryEngine implements InventoryOpener<Player> {
  private final JavaPlugin plugin;
  private final PaperOpenInventoryRegistry registry = new PaperOpenInventoryRegistry();
  private final I18nAdapter<Player> i18nAdapter;

  public PaperNookureInventoryEngine(
      @NotNull JavaPlugin plugin,
      @NotNull String templateFolder,
      @NotNull I18nAdapter<Player> i18nAdapter,
      AbstractExtension... extensions
  ) {
    super(getLoader(plugin, templateFolder), extensions);
    this.i18nAdapter = i18nAdapter;
    this.plugin = plugin;

    registerBukkitEvents();
    registerService();
  }

  private static FileLoader getLoader(JavaPlugin plugin, String templateFolder) {
    FileLoader loader = new FileLoader();
    loader.setPrefix(plugin.getDataFolder().toPath().resolve(templateFolder).toString());

    return loader;
  }

  public void registerBukkitEvents() {
    Bukkit.getPluginManager().registerEvents(new InventoryListener(registry), plugin);
  }

  public void registerService() {
    if (Bukkit.getServicesManager().load(CustomActionRegistry.class) == null) {
      Bukkit.getServicesManager()
          .register(
              CustomActionRegistry.class,
              new CustomActionRegistry(),
              plugin,
              ServicePriority.Normal
          );
    }
  }

  @Override
  public void openAsync(@NotNull Player player, @NotNull String layout, Object... args) {
    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
      GuiLayout guiLayout;

      try {
        if (args.length > 0) {
          guiLayout = parseLayout(layout, args);
        } else {
          guiLayout = parseLayout(layout, "player", player);
        }
      } catch (JAXBException e) {
        throw new RuntimeException(e);
      }

      String permission = guiLayout.head().permission();
      String permissionMessage = guiLayout.head().noPermissionMessage();

      if (permissionMessage == null) {
        permissionMessage = "<red>You do not have permission to open this inventory";
      }

      if (permission != null && !player.hasPermission(permission)) {
        if (ServerUtils.isPaper) {
          player.sendMessage(i18nAdapter.translateComponent(permissionMessage));
        } else {
          player.sendMessage(i18nAdapter.translate(permissionMessage));
        }

        return;
      }

      Bukkit.getScheduler().runTask(plugin, () -> {
        player.openInventory(
            new InventoryContainer(
                player,
                guiLayout,
                i18nAdapter,
                registry,
                this,
                new GuiMetadata(layout, args)
            ).getInventory()
        );
      });
    });
  }

  public static class Builder {
    private JavaPlugin plugin;
    private String templateFolder;
    private I18nAdapter<Player> i18nAdapter;
    private AbstractExtension[] extensions;

    public Builder plugin(JavaPlugin plugin) {
      this.plugin = plugin;
      return this;
    }

    public Builder templateFolder(String templateFolder) {
      this.templateFolder = templateFolder;
      return this;
    }

    public Builder i18nAdapter(I18nAdapter<Player> i18nAdapter) {
      this.i18nAdapter = i18nAdapter;
      return this;
    }

    public Builder extensions(AbstractExtension... extensions) {
      this.extensions = extensions;
      return this;
    }

    public PaperNookureInventoryEngine build() {
      if (plugin == null) {
        throw new IllegalStateException("plugin is required");
      }

      if (templateFolder == null) {
        templateFolder = "gui";
      }

      if (i18nAdapter == null) {
        i18nAdapter = new DefaultPaperAdapter();
      }

      return new PaperNookureInventoryEngine(plugin, templateFolder, i18nAdapter, extensions);
    }
  }
}
