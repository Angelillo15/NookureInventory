package com.nookure.core.inv.parser.item.action.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OpenInventoryPropsAdapter extends TypeAdapter<OpenInventoryProps> {
  @Override
  public void write(JsonWriter out, OpenInventoryProps value) throws IOException {
    out.beginObject();
    out.name("inventoryName").value(value.getInventoryName());
    out.name("context").beginObject();
    if (value.getContext() != null) {
      for (Map.Entry<String, Object> entry : value.getContext().entrySet()) {
        out.name(entry.getKey());
        if (entry.getValue() instanceof String) {
          out.value((String) entry.getValue());
        } else if (entry.getValue() instanceof Number) {
          out.value((Number) entry.getValue());
        } else if (entry.getValue() instanceof Boolean) {
          out.value((Boolean) entry.getValue());
        } else {
          out.value(entry.getValue().toString());
        }
      }
    }
    out.endObject();
    out.endObject();
  }

  @Override
  public OpenInventoryProps read(JsonReader in) throws IOException {
    String inventoryName = null;
    Map<String, Object> context = new HashMap<>();

    in.beginObject();
    while (in.hasNext()) {
      String name = in.nextName();
      if (name.equals("inventoryName")) {
        inventoryName = in.nextString();
      } else if (name.equals("context")) {
        context = readContext(in);
      }
    }
    in.endObject();

    return new OpenInventoryProps(inventoryName, context);
  }

  private Map<String, Object> readContext(JsonReader in) throws IOException {
    Map<String, Object> context = new HashMap<>();

    in.beginObject();
    while (in.hasNext()) {
      String name = in.nextName();
      context.put(name, in.nextString());
    }
    in.endObject();

    return context;
  }
}
