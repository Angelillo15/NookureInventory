package com.nookure.core.inv.parser.item;

import com.nookure.core.inv.parser.adapters.MiniMessageAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlValue;

import java.util.List;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class LiteralLore {
  @XmlValue
  private String literalLore;

  public String literalLore() {
    return MiniMessageAdapter.format(literalLore);
  }

  public List<String> loreLines() {
    return Stream.of(MiniMessageAdapter.format(literalLore).split("\n")).map(String::trim).toList();
  }

  public LiteralLore setLiteralLore(String literalLore) {
    this.literalLore = literalLore;
    return this;
  }
}