package com.nookure.core.inv;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GuiMetadata {
  private final String layout;
  private final Object[] args;
  private final boolean isPagination;
  private final int nextPage;
  private final int previousPage;

  public GuiMetadata(
      @NotNull String layout,
      @NotNull Object... args
  ) {
    this.layout = layout;
    this.args = args;

    Map<String, Object> context = NookureInventoryEngine.toMap(args);
    this.isPagination = context.containsKey("page");

    if (isPagination) {
      int page = (int) context.get("page");
      this.nextPage = page + 1;
      this.previousPage = page - 1;
    } else {
      this.nextPage = -1;
      this.previousPage = -1;
    }
  }

  public boolean isPagination() {
    return isPagination;
  }

  public String layout() {
    return layout;
  }

  public Object[] args() {
    return args;
  }

  public int nextPage() {
    return nextPage;
  }

  public int previousPage() {
    return previousPage;
  }
}
