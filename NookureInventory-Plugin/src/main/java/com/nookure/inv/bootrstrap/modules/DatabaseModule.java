 package com.nookure.inv.bootrstrap.modules;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.core.config.ConfigurationContainer;
import com.nookure.core.database.SQLPluginConnection;
import com.nookure.core.logger.Logger;
import com.nookure.inv.bootrstrap.BootstrapModule;
import com.nookure.inv.config.PaperConfig;
import io.ebean.Database;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class DatabaseModule implements BootstrapModule {
  @Inject
  private AtomicReference<SQLPluginConnection> sqlPluginConnection;
  @Inject
  AtomicReference<Database> database;
  @Inject
  private ConfigurationContainer<PaperConfig> config;
  @Inject
  private Logger logger;
  @Inject
  private Injector injector;
  @Inject
  private JavaPlugin plugin;

  @Override
  public void onEnable() {
    SQLPluginConnection connection = injector.getInstance(SQLPluginConnection.class);
    connection.connect(config.get().database(), this.getClass().getClassLoader());
    sqlPluginConnection.set(connection);

    if (database.get() == null) {
      logger.severe("Database connection failed, disabling plugin.");
      Bukkit.getPluginManager().disablePlugin(plugin);
    }
  }

  @Override
  public void onDisable() {
    if (sqlPluginConnection.get() != null) {
      sqlPluginConnection.get().close();
    }

    if (database.get() != null) {
      database.get().shutdown(true, false);
    }
  }
}
