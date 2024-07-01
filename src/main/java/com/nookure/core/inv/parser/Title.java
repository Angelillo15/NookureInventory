package com.nookure.core.inv.parser;

import com.nookure.core.inv.parser.adapters.MiniMessageAdapter;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Title {
  @XmlValue
  private String title;

  @XmlAttribute(name = "tl")
  private boolean tl;

  public String title() {
    return MiniMessageAdapter.format(title);
  }

  public Title setTitle(String title) {
    this.title = title;
    return this;
  }

  public boolean tl() {
    return tl;
  }

  public Title setTl(boolean tl) {
    this.tl = tl;
    return this;
  }
}
