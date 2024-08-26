package com.nookure.inv.extension.reflection;

import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class RunStaticMethodFunction implements Function {
  private final TemplateExceptionService exceptionService = TemplateExceptionService.INSTANCE;

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    final UUID templateUUID = (UUID) context.getVariable("templateUUID");

    Class<?> clazz;

    try {
      clazz = (Class<?>) args.get("class");
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Class must be a class", lineNumber));
      return null;
    }

    if (clazz == null) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Class cannot be null", lineNumber));
      return null;
    }

    String methodName;

    try {
      methodName = (String) args.get("method");
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Method name must be a string", lineNumber));
      return null;
    }

    if (methodName == null) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Method name cannot be null", lineNumber));
      return null;
    }

    Object[] methodArgs;

    try {
      if (args.get("args") instanceof List) {
        methodArgs = ((List<?>) args.get("args")).toArray();
      } else {
        methodArgs = (Object[]) args.get("args");
      }
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Method arguments must be an array", lineNumber));
      return null;
    }

    if (methodArgs == null) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Method arguments cannot be null", lineNumber));
      return null;
    }

    Class<?>[] argTypes = new Class<?>[methodArgs.length];

    for (int i = 0; i < methodArgs.length; i++) {
      argTypes[i] = methodArgs[i].getClass();
    }

    try {
      return clazz.getMethod(methodName, argTypes).invoke(null, methodArgs);
    } catch (Exception e) {
      exceptionService.addException(templateUUID, new TemplateException(500, "Failed to invoke method: " + e.getMessage(), lineNumber));
      return null;
    }
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("class", "method", "args");
  }
}
