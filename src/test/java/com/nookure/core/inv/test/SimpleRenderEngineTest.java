package com.nookure.core.inv.test;

import com.nookure.core.inv.NookureInventoryEngine;
import io.pebbletemplates.pebble.loader.StringLoader;
import org.junit.jupiter.api.Test;

public class SimpleRenderEngineTest {
  @Test
  public void test() {
    NookureInventoryEngine engine = new NookureInventoryEngine(new StringLoader());

    String result = engine.renderTemplate("""
        <test>
          Hello {{ name }}!, your age is {{ age }}.
        </test>
        """, "name", "Angelillo15", "age", 16);

    assert result.equals("<test>\n  Hello Angelillo15!, your age is 16.\n</test>\n");
  }
}
