package com.nookure.inv.extension;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.inv.extension.database.GetGlobalVariable;
import com.nookure.inv.extension.database.GetPlayerVariable;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;

import java.util.Map;

public final class DatabaseExtension extends AbstractExtension {
  private final Injector injector;

  @Inject
  public DatabaseExtension(Injector injector) {
    this.injector = injector;
  }

  @Override
  public Map<String, Function> getFunctions() {
    return Map.of(
        "getGlobalVariable", injector.getInstance(GetGlobalVariable.class),
        "getPlayerVariable", injector.getInstance(GetPlayerVariable.class)
    );
  }
}
