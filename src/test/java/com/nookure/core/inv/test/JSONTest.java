package com.nookure.core.inv.test;

import com.google.gson.Gson;
import com.nookure.core.inv.parser.item.action.json.OpenInventoryProps;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

public class JSONTest {
  @Test
  public void serializeTest() {
    OpenInventoryProps props = new OpenInventoryProps("test", Map.of("player", UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")));
    Gson gson = new Gson();
    String json = gson.toJson(props);

    assert json.equals("{\"inventoryName\":\"test\",\"context\":{\"player\":\"f47ac10b-58cc-4372-a567-0e02b2c3d479\"}}");
  }

  @Test
  public void deserializeTest() {
    String json = "{\"inventoryName\":\"test\",\"context\":{\"player\":\"f47ac10b-58cc-4372-a567-0e02b2c3d479\"}}";
    Gson gson = new Gson();
    OpenInventoryProps props = gson.fromJson(json, OpenInventoryProps.class);

    assert props.getInventoryName().equals("test");
  }
}
