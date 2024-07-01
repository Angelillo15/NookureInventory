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

    for (Item item : guiLayout.items().itemList()) {
      System.out.println("Item ID: " + item.id());
      System.out.println("Item Slot: " + item.slot());
      System.out.println("Item Material: " + item.material());
      System.out.println("Item Name: " + item.name());
      for (String loreLine : item.lore().loreLines()) {
        System.out.println("Lore Line: " + loreLine);
      }
    }
  }
}
