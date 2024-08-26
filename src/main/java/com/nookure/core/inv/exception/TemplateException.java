package com.nookure.core.inv.exception;

import org.jetbrains.annotations.Nullable;

public record TemplateException(
    int statusCode,
    @Nullable String reason,
    int lineNumber
) {
}
