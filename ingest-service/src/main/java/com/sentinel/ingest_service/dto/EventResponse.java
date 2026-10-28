package com.sentinel.ingest_service.dto;

import java.time.Instant;

public record EventResponse(Integer id, String service, String level, String message, String traceId, Instant occurredAt) {
}
