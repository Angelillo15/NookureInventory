package com.nookure.inv.extension.reflection;

import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ToListFunction implements Function {
  private final TemplateExceptionService exceptionService = TemplateExceptionService.INSTANCE;

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    final UUID templateUUID = (UUID) context.getVariable("templateUUID");

    Object[] array;

    try {
      array = (Object[]) args.get("array");
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Array must be an array", lineNumber));
      return null;
    }

    return List.of(array);
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("array");
  }
}
