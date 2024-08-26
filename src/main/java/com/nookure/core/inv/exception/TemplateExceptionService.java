package com.nookure.core.inv.exception;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ServiceLoader;
import java.util.UUID;

public interface TemplateExceptionService {
  TemplateExceptionService INSTANCE = ServiceLoader.load(TemplateExceptionService.class).findFirst().orElseThrow();

  /**
   * Adds an exception to the template.
   *
   * @param templateUUID The UUID of the current template is being processed.
   * @param exception    The exception to add.
   */
  void addException(@NotNull UUID templateUUID, @NotNull TemplateException exception);

  /**
   * Gets all exceptions for the template.
   *
   * @param templateUUID The UUID of the current template is being processed.
   * @return A list of exceptions.
   */
  List<TemplateException> getExceptionsAndClear(@NotNull UUID templateUUID);
}
