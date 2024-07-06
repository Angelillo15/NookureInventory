package com.nookure.core.inv.template.extension;

import com.nookure.core.inv.parser.item.action.json.OpenInventoryProps;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class OpenInventoryExtension extends AbstractExtension implements Function {
  @Override
  public Map<String, Function> getFunctions() {
    return Map.of("inventoryProps", this);
  }

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    String name = (String) args.get("name");
    Object ctx = args.get("context");
    if (ctx == null) {
      return new OpenInventoryProps(name);
    } else {
      return new OpenInventoryProps(name, (Map<String, Object>) ctx);
    }
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("name", "context");
  }
}
