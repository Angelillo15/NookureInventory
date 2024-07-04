package com.nookure.core.inv.paper.utils;

public class ServerUtils {
  public static final boolean isPaper;

  static {
    isPaper = hasClass("com.destroystokyo.paper.PaperConfig") ||
        hasClass("io.papermc.paper.configuration.Configuration");
  }

  private static boolean hasClass(String className) {
    try {
      Class.forName(className);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }
}
