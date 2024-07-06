package com.nookure.core.inv.parser.item.action;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Action {
  @XmlAttribute(name = "type")
  private String type;

  @XmlAttribute(name = "value")
  private String value;

  public String type() {
    return type;
  }

  public Action setType(String type) {
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
