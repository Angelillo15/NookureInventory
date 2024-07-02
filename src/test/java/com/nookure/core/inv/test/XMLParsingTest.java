package com.nookure.core.inv.test;

import com.nookure.core.inv.parser.GuiLayout;
import com.nookure.core.inv.parser.item.Item;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class XMLParsingTest {
  private static final InputStream xmlStream = XMLParsingTest.class.getClassLoader().getResourceAsStream("GuiLayoutTest.xml");

  @Test
  public void xmlTest() throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(GuiLayout.class);
    Unmarshaller unmarshaller = context.createUnmarshaller();

    GuiLayout guiLayout = (GuiLayout) unmarshaller.unmarshal(xmlStream);

    System.out.println("Title: " + guiLayout.head().title().title());
    System.out.println("Title tl: " + guiLayout.head().title().tl());
    System.out.println("Rows: " + guiLayout.head().rows());
    System.out.println("Permission: " + guiLayout.head().permission());

    for (Item item : guiLayout.items().itemList()) {
      System.out.println("Item ID: " + item.id());
      System.out.println("Item Slot: " + item.slot());
      System.out.println("Item Material: " + item.material());
      System.out.println("Item Name: " + item.name());

      if (item.literalLore() != null) {
        System.out.println("Literal Lore: " + item.literalLore().literalLore());
        item.literalLore().loreLines().forEach(loreLine -> System.out.println("Lore Line: " + loreLine));
      } else {
        if (item.lore().loreLines() == null) continue;

        for (String loreLine : item.lore().loreLines()) {
          System.out.println("Lore Line: " + loreLine);
        }
      }
    }
  }
}
