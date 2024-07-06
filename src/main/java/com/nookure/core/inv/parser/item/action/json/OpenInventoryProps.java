package com.nookure.core.inv.parser.item.action.json;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;

import java.util.Map;

@JsonAdapter(OpenInventoryPropsAdapter.class)
public class OpenInventoryProps {
  private final String inventoryName;
  private final Map<String, Object> context;
  private static final Gson gson = new Gson();

  public OpenInventoryProps(String inventoryName, Map<String, Object> context) {
    this.inventoryName = inventoryName;
    this.context = context;
  }

  public OpenInventoryProps(String inventoryName) {
    this(inventoryName, null);
  }

  public String getInventoryName() {
    return inventoryName;
  }

  public Map<String, Object> getContext() {
    return context;
  }

  @Override
  public String toString() {
    return gson.toJson(this);
  }
}
