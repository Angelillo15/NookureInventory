package com.nookure.core.inv.template.extension;

import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import com.nookure.core.inv.exception.UserFriendlyRuntimeException;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PaginationItemExtension extends AbstractExtension implements Function {
  private final TemplateExceptionService exceptionService = TemplateExceptionService.INSTANCE;

  @Override
  public Map<String, Function> getFunctions() {
    return Map.of("pagination", this);
  }

  public record PaginationData(long start, long end, int lastPage) {
  }

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    UUID templateUUID = (UUID) context.getVariable("templateUUID");
    Long rows;

    try {
      if (args.get("rows") instanceof Integer) {
        rows = ((Integer) args.get("rows")).longValue();
      } else {
        rows = (Long) args.get("rows");
      }
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Rows must be a number", lineNumber));
      return null;
    }

    Long columns;

    try {
      if (args.get("columns") instanceof Integer) {
        columns = ((Integer) args.get("columns")).longValue();
      } else {
        columns = (Long) args.get("columns");
      }
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Columns must be a number", lineNumber));
      return null;
    }

    Long page;

    try {
      if (args.get("page") instanceof Integer) {
        page = ((Integer) args.get("page")).longValue();
      } else {
        page = (Long) args.get("page");
      }
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Page must be a number", lineNumber));
      return null;
    }

    Long total;

    try {
      if (args.get("total") instanceof Integer) {
        total = ((Integer) args.get("total")).longValue();
      } else {
        total = (Long) args.get("total");
      }
    } catch (ClassCastException e) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Total must be a number", lineNumber));
      return null;
    }

    if (rows == null || columns == null || page == null || total == null) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Pagination arguments must be provided", lineNumber));
      return null;
    }

    if (rows < 0 || columns < 0 || page < 0 || total < 0) {
      exceptionService.addException(templateUUID, new TemplateException(400, "Pagination arguments must be positive", lineNumber));
      return null;
    }

    long start = (page - 1) * rows * columns;
    long end = Math.min(start + rows * columns, total) - 1;
    int lastPage = (int) Math.ceil((double) total / (rows * columns));

    return new PaginationData(start, end, lastPage);
  }

  @Override
  public List<String> getArgumentNames() {
    return List.of("rows", "columns", "page", "total");
  }
}
