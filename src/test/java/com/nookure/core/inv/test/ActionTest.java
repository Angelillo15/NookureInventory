package com.nookure.core.inv.test;

import com.nookure.core.inv.NookureInventoryEngine;
import com.nookure.core.inv.parser.GuiLayout;
import com.nookure.core.inv.parser.item.action.Action;
import com.nookure.core.inv.parser.item.Item;
import io.pebbletemplates.pebble.loader.StringLoader;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ActionTest {
  private static final InputStream xmlStream = XMLParsingTest.class.getClassLoader().getResourceAsStream("GuiLayoutTest.xml");
  @Test
  public void action() throws JAXBException, ParserConfigurationException, SAXException {
    GuiLayout guiLayout = guiLayout(xmlStream);

    System.out.println("Title: " + guiLayout.head().title().title());
    System.out.println("Title tl: " + guiLayout.head().title().tl());
    System.out.println("Rows: " + guiLayout.head().rows());

    for (Item item : guiLayout.items().itemList()) {
      System.out.println("Item ID: " + item.id());
      System.out.println("Item Slot: " + item.slot());
      System.out.println("Item Material: " + item.material());
      System.out.println("Item Name: " + item.name());

      if (item.actions() == null){
        continue;
      }

      for (Action action : item.actions().actionsList()) {
        System.out.println("Action type: " + action.type());

        if (action.value() != null) {
          System.out.println("Action value: " + action.value());
        }
      }
    }
  }

  public static GuiLayout guiLayout(InputStream xmlStream) throws JAXBException, ParserConfigurationException, SAXException {
    NookureInventoryEngine engine = new NookureInventoryEngine(new StringLoader());
    StringBuilder textBuilder = new StringBuilder();
    assert xmlStream != null;

    try (Reader reader = new BufferedReader(new InputStreamReader
        (xmlStream, StandardCharsets.UTF_8))) {
      int c = 0;
      while ((c = reader.read()) != -1) {
        textBuilder.append((char) c);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return engine.parseLayout(textBuilder.toString());
  }
}
