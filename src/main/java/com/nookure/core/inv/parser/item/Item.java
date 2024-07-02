package com.nookure.core.inv.parser.item;

import com.nookure.core.inv.parser.adapters.MiniMessageAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
  @XmlAttribute(name = "id")
  private String id;

  @XmlAttribute(name = "slot")
  private int slot;

  @XmlAttribute(name = "material")
  private String material;

  @XmlElement(name = "Name")
  @XmlJavaTypeAdapter(MiniMessageAdapter.class)
  private String name;

  @XmlElement(name = "tl")
  private boolean tl;

  @XmlAttribute(name = "enchanted")
  private boolean enchanted;

  @XmlElement(name = "Lore")
  private Lore lore;

  @XmlElement(name = "LiteralLore")
  private LiteralLore literalLore;

  @XmlElement(name = "Actions")
  private Actions actions;

  public String id() {
    return id;
  }

  public Item setId(String id) {
    this.id = id;
    return this;
  }

  public int slot() {
    return slot;
  }

  public Item setSlot(int slot) {
    this.slot = slot;
    return this;
  }

  public String material() {
    return material;
  }

  public Item setMaterial(String material) {
    this.material = material;
    return this;
  }

  public String name() {
    return name;
  }

  public Item setName(String name) {
    this.name = name;
    return this;
  }

  public Lore lore() {
    return lore;
  }

  public Item setLore(Lore lore) {
    this.lore = lore;
    return this;
  }

  public boolean tl() {
    return tl;
  }

  public Item setTl(boolean tl) {
    this.tl = tl;
    return this;
  }

  public boolean enchanted() {
    return enchanted;
  }

  public Item setEnchanted(boolean enchanted) {
    this.enchanted = enchanted;
    return this;
  }

  public Actions actions() {
    return actions;
  }

  public Item setActions(Actions actions) {
    this.actions = actions;
    return this;
  }

  public LiteralLore literalLore() {
    return literalLore;
  }

  public Item setLiteralLore(LiteralLore literalLore) {
    this.literalLore = literalLore;
    return this;
  }
}
