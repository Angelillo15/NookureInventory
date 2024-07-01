package com.nookure.core.inv.parser.item;

import com.nookure.core.inv.parser.adapters.MiniMessageAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Lore")
public class Lore {
  @XmlElement(name = "LoreLine")
  @XmlJavaTypeAdapter(MiniMessageAdapter.class)
  private List<String> loreLines;

  public List<String> loreLines() {
    return loreLines;
  }

  public Lore setLoreLines(List<String> loreLines) {
    this.loreLines = loreLines;
    return this;
  }
}