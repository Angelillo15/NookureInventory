package com.nookure.core.inv.parser.item;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Actions {
  @XmlElement(name = "Action")
  private List<Action> actionsList;

  public List<Action> actionsList() {
    return actionsList;
  }

  public Actions setActionsList(List<Action> actionsList) {
    this.actionsList = actionsList;
    return this;
  }
}
