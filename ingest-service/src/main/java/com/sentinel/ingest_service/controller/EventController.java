package com.sentinel.ingest_service.controller;

import com.sentinel.ingest_service.dto.EventRequest;
import com.sentinel.ingest_service.entity.Event;
import com.sentinel.ingest_service.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping
    public ResponseEntity<Event> create(@Valid @RequestBody EventRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(req));
    }

    @GetMapping
    public ResponseEntity<Page<Event>> list(
            @RequestParam(required = false) String service,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(eventService.listEvents(service, page, size));
    }
}
