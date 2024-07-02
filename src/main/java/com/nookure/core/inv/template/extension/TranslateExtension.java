package com.nookure.core.inv.template.extension;

import com.nookure.core.inv.I18nAdapter;
import com.nookure.core.inv.exception.UserFriendlyRuntimeException;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class TranslateExtension extends AbstractExtension implements Function {
  private final I18nAdapter<Object> adapter;

  @Inject
  public TranslateExtension(I18nAdapter<Object> adapter) {
    this.adapter = adapter;
  }

  @Override
  public Map<String, Function> getFunctions() {
    return Map.of("tl", this);
  }

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    String key = (String) args.get("key");
    Object player = args.get("player");

    if (key == null) {
      throw new UserFriendlyRuntimeException("Missing required argument 'key' --> tl(key: string, @Optional player: object)");
    }

    if (player == null) {
      return adapter.translate(key, true, null);
    }

    return adapter.translate(key, true, player);
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("key", "player");
  }
}
