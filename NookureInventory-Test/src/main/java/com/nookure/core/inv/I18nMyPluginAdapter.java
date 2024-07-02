package com.nookure.core.inv;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class I18nMyPluginAdapter extends I18nAdapter<Player> {
  @Override
  public String translate(String key, boolean isKey, Player player) {
    return key;
  }

  @Override
  public Component translateComponent(String key, boolean isKey, Player player) {
    return MiniMessage.miniMessage().deserialize(key);
  }
}
