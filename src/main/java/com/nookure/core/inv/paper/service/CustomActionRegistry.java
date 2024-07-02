package com.nookure.core.inv.paper.service;

import com.nookure.core.inv.paper.CustomPaperAction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CustomActionRegistry {
  private final Map<String, CustomPaperAction> actions = new HashMap<>();

  /**
   * Register an action
   *
   * @param action The action to register
   */
  public void registerAction(CustomPaperAction action) {
    actions.put(action.getActionData().value(), action);
  }

  /**
   * Get an action by key
   *
   * @param key The key of the action
   * @return The action
   */
  @Nullable
  public CustomPaperAction getAction(String key) {
    return actions.get(key);
  }

  /**
   * Check if an action exists
   *
   * @param key The key of the action
   * @return If the action exists
   */
  public boolean hasAction(String key) {
    return actions.containsKey(key);
  }
}
