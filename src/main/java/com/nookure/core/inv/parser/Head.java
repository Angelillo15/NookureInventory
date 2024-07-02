package com.nookure.core.inv.parser;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Head {
  @XmlElement(name = "title")
  private Title title;

  @XmlElement(name = "rows")
  private int rows;

  @XmlElement(name = "permission")
  private String permission;

  @XmlElement(name = "noPermissionMessage")
  private String noPermissionMessage;

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

  public String permission() {
    return permission;
  }

  public Head setPermission(String permission) {
    this.permission = permission;
    return this;
  }

  public String noPermissionMessage() {
    return noPermissionMessage;
  }

  public Head setNoPermissionMessage(String noPermissionMessage) {
    this.noPermissionMessage = noPermissionMessage;
    return this;
  }
}