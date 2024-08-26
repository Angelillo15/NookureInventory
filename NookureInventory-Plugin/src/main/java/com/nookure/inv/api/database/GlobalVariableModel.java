package com.nookure.inv.api.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "nookure_inventory_global_variables")
public class GlobalVariableModel extends BaseModel {
  @Column(nullable = false)
  String key;
  @Column(nullable = false)
  String value;

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

  public GlobalVariable toGlobalVariable() {
    return GlobalVariable.builder()
        .key(key)
        .value(value)
        .build();
  }

  public static GlobalVariableModel fromGlobalVariable(GlobalVariable globalVariable) {
    GlobalVariableModel globalVariableModel = new GlobalVariableModel();
    globalVariableModel.setKey(globalVariable.key());
    globalVariableModel.setValue(globalVariable.value());
    return globalVariableModel;
  }
}
