package com.nookure.core.inv.paper;

import com.nookure.core.inv.I18nAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class DefaultPaperAdapter extends I18nAdapter<Player> {

  @Override
  public String translate(String key, boolean isKey, @Nullable Player player) {
    return LegacyComponentSerializer.legacySection().serialize(translateComponent(key, isKey, player));
  }

  @Override
  public Component translateComponent(String key, boolean isKey, @Nullable Player player) {
    return MiniMessage.miniMessage().deserialize(key);
  }
}
