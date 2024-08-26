package com.nookure.inv.extension.reflection;

import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class RunMethodFunction implements Function {
  private final TemplateExceptionService exceptionService = TemplateExceptionService.INSTANCE;

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    final UUID templateUUID = (UUID) context.getVariable("templateUUID");

    Object object;

    try {
      object = args.get("object");
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Object must be an object", lineNumber));
      return null;
    }

    if (object == null) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Object cannot be null", lineNumber));
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

    try {
      return object.getClass().getMethod(methodName).invoke(object, methodArgs);
    } catch (Exception e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Failed to run method", lineNumber));
      return null;
    }
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("object", "method", "args");
  }
}
