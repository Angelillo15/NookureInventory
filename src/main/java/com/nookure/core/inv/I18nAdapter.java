package com.nookure.core.inv;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public abstract class I18nAdapter<T> {
  /**
   * Translate a key or a text to a string
   *
   * @param key   The key to translate
   * @param isKey If the key is a key or a text
   * @return The translated string
   */
  public abstract String translate(String key, boolean isKey, @Nullable T player);

  /**
   * Translate a key or a text to a component
   *
   * @param key   The key to translate
   * @param isKey If the key is a key or a text
   * @return The translated component
   */
  public abstract Component translateComponent(String key, boolean isKey, @Nullable T player);

  /**
   * Translate a key or a text to a string
   *
   * @param key   The key to translate
   * @param isKey If the key is a key or a text
   * @return The translated string
   */
  public String translate(String key, boolean isKey) {
    return translate(key, isKey, null);
  }

  /**
   * Translate a key or a text to a component
   *
   * @param key   The key to translate
   * @param isKey If the key is a key or a text
   * @return The translated component
   */
  public Component translateComponent(String key, boolean isKey) {
    return translateComponent(key, isKey, null);
  }

  /**
   * Translate text to a string
   *
   * @param key The key to translate
   * @return The translated string
   */
  public String translate(String key) {
    return translate(key, false);
  }

  /**
   * Translate text to a component
   *
   * @param key The key to translate
   * @return The translated component
   */
  public Component translateComponent(String key) {
    return translateComponent(key, false);
  }
}
