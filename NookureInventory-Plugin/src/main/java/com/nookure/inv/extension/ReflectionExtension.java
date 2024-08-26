package com.nookure.inv.extension;

import com.nookure.inv.extension.reflection.GetClassFunction;
import com.nookure.inv.extension.reflection.RunMethodFunction;
import com.nookure.inv.extension.reflection.RunStaticMethodFunction;
import com.nookure.inv.extension.reflection.ToListFunction;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;

import java.util.Map;

public class ReflectionExtension extends AbstractExtension {
  @Override
  public Map<String, Function> getFunctions() {
    return Map.of(
        "getClass", new GetClassFunction(),
        "runStaticMethod", new RunStaticMethodFunction(),
        "runMethodFunction", new RunMethodFunction(),
        "toList", new ToListFunction()
    );
  }
}
