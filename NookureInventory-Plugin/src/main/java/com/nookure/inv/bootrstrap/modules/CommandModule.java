package com.nookure.inv.bootrstrap.modules;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.nookure.core.config.ConfigurationContainer;
import com.nookure.core.logger.Logger;
import com.nookure.inv.bootrstrap.BootstrapModule;
import com.nookure.inv.command.GUICommand;
import com.nookure.inv.config.CommandConfig;
import com.nookure.inv.config.partial.CommandPartial;
import com.nookure.inv.config.partial.CommandPartialConfig;
import com.nookure.inv.factory.GUICommandFactory;
import org.bukkit.command.CommandMap;

import java.util.List;

@Singleton
public class CommandModule implements BootstrapModule {
  @Inject
  private ConfigurationContainer<CommandConfig> commandConfig;
  @Inject
  private CommandMap commandMap;
  @Inject
  private Logger logger;
  @Inject
  private GUICommandFactory factory;

  @Override
  public void onEnable() {
    final List<CommandPartialConfig> commands = commandConfig.get().commands();

    for (CommandPartialConfig commandPartialConfig : commands) {
      CommandPartial command = commandPartialConfig.toCommandPartial();

      try {
        commandMap.register(command.fallback() == null ? "nookure" : command.fallback(), factory.create(command));
        logger.debug("Registered command: %s", command.command());
      } catch (Exception e) {
        logger.severe("An error occurred while registering command: %s", command.command());
        logger.severe(e);
      }
    }
  }
}
