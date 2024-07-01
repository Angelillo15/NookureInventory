package com.nookure.core.inv;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class I18nMyPluginAdapter extends I18nAdapter {
  @Override
  public String translate(String key, boolean isKey) {
    return key;
  }

  @Override
  public Component translateComponent(String key, boolean isKey) {
    return MiniMessage.miniMessage().deserialize(key);
  }
}
