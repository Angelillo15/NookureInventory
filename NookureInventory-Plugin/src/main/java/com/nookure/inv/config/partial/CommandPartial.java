package com.nookure.inv.config.partial;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoValue
public abstract class CommandPartial {

  public abstract String command();

  @Nullable
  public abstract List<String> aliases();

  @Nullable
  public abstract String permission();

  @Nullable
  public abstract String description();

  @Nullable
  public abstract String usage();

  public abstract String inventory();

  @Nullable
  public abstract String fallback();

  public static Builder builder() {
    return new AutoValue_CommandPartial.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder command(String command);

    public abstract Builder aliases(@Nullable List<String> aliases);

    public abstract Builder permission(@Nullable String permission);

    public abstract Builder description(@Nullable String description);

    public abstract Builder usage(@Nullable String usage);

    public abstract Builder inventory(String inventory);

    public abstract Builder fallback(@Nullable String fallback);

    public abstract CommandPartial build();
  }
}
