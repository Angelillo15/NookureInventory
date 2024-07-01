package com.nookure.core.inv.parser;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Head {
  @XmlElement(name = "Title")
  private Title title;

  @XmlElement(name = "rows")
  private int rows;

  public Title title() {
    return title;
  }

  public Head setTitle(Title title) {
    this.title = title;
    return this;
  }

  public int rows() {
    return rows;
  }

  public Head setRows(int rows) {
    this.rows = rows;
    return this;
  }
}