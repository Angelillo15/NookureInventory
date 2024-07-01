package com.nookure.core.inv.parser.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MiniMessageAdapter extends XmlAdapter<Object, String> {
  @Override
  public String unmarshal(Object v) {
    if (v instanceof Element element) {
      return format(element.getTextContent());
    } else if (v instanceof String) {
      return (String) v;
    }
    return null;
  }

  @Override
  public Object marshal(String v) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.newDocument();
    Element element = document.createElement("content");
    element.setTextContent(v);
    return element;
  }

  public static String format(String message) {
    String mm = message.replace("[", "<").replace("]", ">");
    mm = mm.trim();
    mm = mm.replaceAll("^\\n+|\\n+$", "");
    return mm;
  }
}
