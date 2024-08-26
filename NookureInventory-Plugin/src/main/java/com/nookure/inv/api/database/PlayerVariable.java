package com.nookure.inv.api.database;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AutoValue
public abstract class PlayerVariable {
  @NotNull
  public abstract UUID playerUUID();

  @NotNull
  public abstract String key();

  @NotNull
  public abstract String value();

  public static Builder builder() {
    return new AutoValue_PlayerVariable.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder playerUUID(@NotNull UUID playerUUID);

    public abstract Builder key(@NotNull String key);

    public abstract Builder value(@NotNull String value);

    public abstract PlayerVariable build();
  }
}
