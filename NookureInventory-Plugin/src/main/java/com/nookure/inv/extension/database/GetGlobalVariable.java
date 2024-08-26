package com.nookure.inv.extension.database;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import com.nookure.inv.api.database.GlobalVariableModel;
import io.ebean.Database;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class GetGlobalVariable implements Function {
  private final TemplateExceptionService exceptionService = TemplateExceptionService.INSTANCE;
  private final AtomicReference<Database> database;
  private final Cache<String, String> cache = Caffeine
      .newBuilder()
      .expireAfterAccess(30, TimeUnit.SECONDS) //TODO make this configurable, maybe?
      .build();

  @Inject
  public GetGlobalVariable(AtomicReference<Database> database) {
    this.database = database;
  }

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    final UUID templateUUID = (UUID) context.getVariable("templateUUID");
    String key;

    try {
      key = args.get("key").toString();
    } catch (NullPointerException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Key cannot be null", lineNumber));
      return null;
    }

    if (cache.asMap().containsKey(key)) {
      return cache.getIfPresent(key);
    }

    Object defaultValue = args.get("default");

    GlobalVariableModel model = database.get().find(GlobalVariableModel.class).where().eq("key", key).findOne();

    if (model == null) {
      return defaultValue;
    }

    cache.put(key, model.value());
    return model.value();
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("key", "default");
  }
}
