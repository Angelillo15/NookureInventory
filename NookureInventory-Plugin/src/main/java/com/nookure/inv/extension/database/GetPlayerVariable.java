package com.nookure.inv.extension.database;

import com.google.inject.Inject;
import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import com.nookure.inv.api.database.PlayerVariableModel;
import io.ebean.Database;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public final class GetPlayerVariable implements Function {
  private final TemplateExceptionService exceptionService = TemplateExceptionService.INSTANCE;
  private final AtomicReference<Database> database;

  @Inject
  public GetPlayerVariable(AtomicReference<Database> database) {
    this.database = database;
  }

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    final UUID templateUUID = (UUID) context.getVariable("templateUUID");

    Player player;

    try {
      player = (Player) context.getVariable("player");
    } catch (NullPointerException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Player cannot be null", lineNumber));
      return null;
    }

    String key;

    try {
      key = args.get("key").toString();
    } catch (NullPointerException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Key cannot be null", lineNumber));
      return null;
    }

    String defaultValue = null;

    try {
      if (!(args.get("default") == null)) defaultValue = args.get("default").toString();
    } catch (NullPointerException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Default value cannot be null", lineNumber));
      return null;
    }

    PlayerVariableModel model = database.get().find(PlayerVariableModel.class)
        .where()
        .eq("player", player.getUniqueId().toString())
        .eq("key", key).findOne();

    if (model == null) {
      return defaultValue;
    }

    return model.value();
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("key", "default");
  }
}
