package com.nookure.core.inv.parser;

import com.nookure.core.inv.parser.item.Items;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GuiLayout")
@XmlAccessorType(XmlAccessType.FIELD)
public class GuiLayout {
  @XmlElement(name = "Head")
  private Head head;

  @XmlElement(name = "Items")
  private Items items;

  public Head head() {
    return head;
  }

  public GuiLayout setHead(Head head) {
    this.head = head;
    return this;
  }

  public Items items() {
    return items;
  }

  public GuiLayout setItems(Items items) {
    this.items = items;
    return this;
  }
}
