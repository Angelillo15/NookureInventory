package com.nookure.core.inv.parser.item;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
  @XmlElement(name = "Item")
  private List<Item> itemList;

  public List<Item> itemList() {
    return itemList;
  }

  public Items setItemList(List<Item> itemList) {
    this.itemList = itemList;
    return this;
  }
}