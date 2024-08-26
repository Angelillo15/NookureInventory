package com.nookure.inv.factory;

import com.nookure.inv.command.GUICommand;
import com.nookure.inv.config.partial.CommandPartial;
import org.jetbrains.annotations.NotNull;

public interface GUICommandFactory {
  /**
   * Create a new GUICommand instance.
   *
   * @param commandPartial The command partial.
   * @return The GUICommand instance injected with the command partial.
   */
  @NotNull
  GUICommand create(@NotNull CommandPartial commandPartial);
}