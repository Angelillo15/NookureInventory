package com.nookure.core.inv.template.extension;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class PaginationItemExtension extends AbstractExtension implements Function {
  @Override
  public Map<String, Function> getFunctions() {
    return Map.of("pagination", this);
  }

  public record PaginationData(long start, long end, int lastPage) {}

  @Override
  public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {;
    Long rows = (Long) args.get("rows");
    Long columns = (Long) args.get("columns");
    Integer page = (Integer) args.get("page");
    Integer total = (Integer) args.get("total");

    if (rows == null || columns == null || page == null || total == null) {
      throw new IllegalArgumentException("Missing required arguments");
    }

    if (rows < 0 || columns < 0 || page < 0 || total < 0) {
      throw new IllegalArgumentException("Arguments must be greater than 0");
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
