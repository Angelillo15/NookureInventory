package com.nookure.core.inv.exception.impl;

import com.google.auto.service.AutoService;
import com.nookure.core.inv.exception.TemplateException;
import com.nookure.core.inv.exception.TemplateExceptionService;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.requireNonNull;

@AutoService(TemplateExceptionService.class)
public class TemplateExceptionServiceImpl implements TemplateExceptionService {
  private final ConcurrentHashMap<UUID, CopyOnWriteArrayList<TemplateException>> exceptions = new ConcurrentHashMap<>();

  @Override
  public void addException(@NotNull UUID templateUUID, @NotNull TemplateException exception) {
    requireNonNull(templateUUID, "Template UUID cannot be null.");
    requireNonNull(exception, "Exception cannot be null.");

    exceptions.computeIfAbsent(templateUUID, k -> new CopyOnWriteArrayList<>()).add(exception);
  }

  @Override
  public List<TemplateException> getExceptionsAndClear(@NotNull UUID templateUUID) {
    requireNonNull(templateUUID, "Template UUID cannot be null.");

    if (!exceptions.containsKey(templateUUID)) {
      return List.of();
    }

    return exceptions.remove(templateUUID);
  }
}