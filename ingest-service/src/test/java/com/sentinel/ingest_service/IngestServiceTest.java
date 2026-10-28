package com.sentinel.ingest_service;


import com.sentinel.ingest_service.dto.EventRequest;
import com.sentinel.ingest_service.dto.EventResponse;
import com.sentinel.ingest_service.entity.Event;
import com.sentinel.ingest_service.repository.EventRepository;
import com.sentinel.ingest_service.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IngestServiceTest {

    @Autowired
    EventService eventService;
    @MockitoBean
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
