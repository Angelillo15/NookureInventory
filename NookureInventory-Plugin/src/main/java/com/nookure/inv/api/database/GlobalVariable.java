package com.nookure.inv.api.database;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@AutoValue
public abstract class GlobalVariable {
  @NotNull
  public abstract String key();

  @NotNull
  public abstract String value();

  public static Builder builder() {
    return new AutoValue_GlobalVariable.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder key(@NotNull String key);

    public abstract Builder value(@NotNull String value);

    public abstract GlobalVariable build();
  }
}
