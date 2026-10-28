package com.sentinel.ingest_service;


import com.sentinel.ingest_service.controller.EventController;
import com.sentinel.ingest_service.dto.EventRequest;
import com.sentinel.ingest_service.dto.EventResponse;
import com.sentinel.ingest_service.entity.Event;
import com.sentinel.ingest_service.repository.EventRepository;
import com.sentinel.ingest_service.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngestTest {

    @InjectMocks
    EventService eventService;
    @Mock
    EventRepository eventRepository;

    @Test
    void test_create_valid_event(){
        EventRequest request= new EventRequest("Payment","success","your payment is porcessed sucessfully","123", Instant.now());

        Event event= new Event();
        event.setService(request.service());
        event.setTraceId(request.traceId());
        event.setMessage(request.message());
        event.setLevel(request.level());
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        EventResponse result=eventService.createEvent(request);
        assertEquals(request.message(),result.message());
        verify(eventRepository).save(any(Event.class));
    }


}
