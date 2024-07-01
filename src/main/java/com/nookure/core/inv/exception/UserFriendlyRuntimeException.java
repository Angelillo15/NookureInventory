package com.nookure.core.inv.exception;

import java.io.PrintWriter;
import java.util.logging.Logger;

public class UserFriendlyRuntimeException extends RuntimeException {
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_RESET = "\u001B[0m";
  private final Logger logger = Logger.getLogger("NKInventoryEngine");

  public UserFriendlyRuntimeException() {
    super();
    printWarning();
  }

  public UserFriendlyRuntimeException(String message) {
    super(message);
    printWarning();
  }

  public UserFriendlyRuntimeException(String message, Throwable cause) {
    super(message, cause);
    printWarning();
  }

  public UserFriendlyRuntimeException(Throwable cause) {
    super(cause);
    printWarning();
  }

  protected UserFriendlyRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public void printWarning() {
    println("");
    println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
    println("┃   README README README README README README README README README README  ┃");
    println("┃                                                                          ┃");
    println("┃ An exception have occurred while parsing or performing an action defined ┃");
    println("┃ by you, the user, this isn't a plugin bug or library error in the 99.9%  ┃");
    println("┃ of the cases. Please, read carefully the reason, then ignore the other   ┃");
    println("┃ is just a stack trace (for nerds).                                       ┃");
    println("┃                                                                          ┃");
    println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    println("Reason: ");
    println("-------> "+ getMessage());
    println("");
    println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━JUST IGNORE THIS━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
  }


  private void println(String s) {
    logger.severe(ANSI_RED + s + ANSI_RESET);
  }
}
