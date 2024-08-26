package com.nookure.inv.api.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "nookure_inventory_player_variables")
public class PlayerVariableModel extends BaseModel {
  @Column(nullable = false)
  UUID playerId;
  @Column(nullable = false)
  String key;
  @Column(nullable = false)
  String value;

  public UUID playerId() {
    return playerId;
  }

  public void setPlayerId(UUID playerId) {
    this.playerId = playerId;
  }

  public String key() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String value() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public PlayerVariable toPlayerVariable() {
    return PlayerVariable.builder()
        .playerUUID(playerId)
        .key(key)
        .value(value)
        .build();
  }

  public static PlayerVariableModel fromPlayerVariable(PlayerVariable playerVariable) {
    PlayerVariableModel playerVariableModel = new PlayerVariableModel();
    playerVariableModel.setPlayerId(playerVariable.playerUUID());
    playerVariableModel.setKey(playerVariable.key());
    playerVariableModel.setValue(playerVariable.value());
    return playerVariableModel;
  }
}
