package com.nookure.core.inv.parser.item;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Action {
  @XmlAttribute(name = "type")
  private ActionType type;

  @XmlAttribute(name = "value")
  private String value;

  public ActionType type() {
    return type;
  }

  public Action setType(ActionType type) {
    this.type = type;
    return this;
  }

  public String value() {
    return value;
  }

  public Action setValue(String value) {
    this.value = value;
    return this;
  }
}
