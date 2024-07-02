package com.nookure.core.inv.paper.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomActionData {
  /**
   * The key of the action
   * Should be unique and UpperCamelCase
   *
   * @return The key of the action
   */
  String value();

  /**
   * If the action requires a value
   *
   * @return If the action requires a value
   */
  boolean hasValue() default false;
}
