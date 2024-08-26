package com.nookure.inv.config.partial;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public class CommandPartialConfig {

  @Setting
  private String command;

  @Setting
  private List<String> aliases;

  @Setting
  private String permission;

  @Setting
  private String description;

  @Setting
  private String usage;

  @Setting
  private String inventory;

  @Setting
  private String fallback;

  public CommandPartial toCommandPartial() {
    return CommandPartial.builder()
        .command(command)
        .aliases(aliases)
        .permission(permission)
        .description(description)
        .usage(usage)
        .inventory(inventory)
        .fallback(fallback)
        .build();
  }

  public static CommandPartialConfig fromCommandPartial(CommandPartial commandPartial) {
    CommandPartialConfig config = new CommandPartialConfig();
    config.command = commandPartial.command();
    config.aliases = commandPartial.aliases();
    config.permission = commandPartial.permission();
    config.description = commandPartial.description();
    config.usage = commandPartial.usage();
    config.inventory = commandPartial.inventory();
    config.fallback = commandPartial.fallback();
    return config;
  }
}
