package com.sentinel.ingest_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;

public record EventRequest(
        @NotNull String service,
        @NotNull String level,
        @NotNull String message,
        @NotNull String traceId,
        @NotNull @PastOrPresent Instant occurredAt
) {
}
