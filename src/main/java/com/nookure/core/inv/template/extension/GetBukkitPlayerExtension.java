package com.nookure.core.inv.template.extension;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GetBukkitPlayerExtension extends AbstractExtension implements Function {
  @Override
  public Map<String, Function> getFunctions() {
    return Map.of("getBukkitPlayer", this);
  }

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    String value = (String) args.get("value");

    try {
      return Bukkit.getPlayer(UUID.fromString(value));
    } catch (IllegalArgumentException e) {
      return Bukkit.getPlayer(value);
    }
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("value");
  }
}
