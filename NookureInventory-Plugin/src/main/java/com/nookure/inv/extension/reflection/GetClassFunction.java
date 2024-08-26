package com.nookure.inv.extension.reflection;

import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GetClassFunction implements Function {
  private final TemplateExceptionService exceptionService = TemplateExceptionService.INSTANCE;

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    String className = args.get("class").toString();
    final UUID templateUUID = (UUID) context.getVariable("templateUUID");

    if (className == null) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Class name cannot be null", lineNumber));
    }

    Class<?> clazz;

    try {
      clazz = Class.forName(className);
    } catch (ClassNotFoundException e) {
      exceptionService.addException(templateUUID, new TemplateException(404, "Cannot find class: " + className, lineNumber));
      return null;
    }

    return clazz;
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("class");
  }
}
