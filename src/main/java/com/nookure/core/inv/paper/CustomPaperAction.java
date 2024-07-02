package com.nookure.core.inv.paper;

import com.nookure.core.inv.paper.annotation.CustomActionData;
import com.nookure.core.inv.exception.UserFriendlyRuntimeException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CustomPaperAction {
  private final CustomActionData actionData;

  public CustomPaperAction() {
    this.actionData = getActionData();
  }

  public abstract void execute(Player player, @Nullable String value);

  @NotNull
  public CustomActionData getActionData() {
    if (actionData != null) {
      return actionData;
    }

    CustomActionData annotation = this.getClass().getAnnotation(CustomActionData.class);

    if (annotation == null) {
      throw new UserFriendlyRuntimeException("CustomActionData annotation not found on " + this.getClass().getName());
    }

    return annotation;
  }
}
