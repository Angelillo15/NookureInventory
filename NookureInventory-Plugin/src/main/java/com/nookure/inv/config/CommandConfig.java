package com.nookure.inv.config;

import com.nookure.inv.config.partial.CommandPartial;
import com.nookure.inv.config.partial.CommandPartialConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public class CommandConfig {
  @Setting
  private List<CommandPartialConfig> commands = List.of(
      CommandPartialConfig.fromCommandPartial(CommandPartial
          .builder()
          .command("test")
          .fallback("nookure")
          .aliases(List.of("testAlias"))
          .description("Your test command")
          .inventory("test.peb")
          .permission("nookure.test")
          .build()
      )
  );

  public List<CommandPartialConfig> commands() {
    return commands;
  }
}
