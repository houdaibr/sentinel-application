package com.sentinel.ingest_service;


import com.sentinel.ingest_service.controller.EventController;
import com.sentinel.ingest_service.dto.EventRequest;
import com.sentinel.ingest_service.dto.EventResponse;
import com.sentinel.ingest_service.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class IngestControllerTest {
    @Autowired
    ObjectMapper obj;

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    EventService svc;


    @Test
    void create_valid_event() throws Exception {
        EventRequest request= new EventRequest("Payment","success","your payment is porcessed sucessfully","123", Instant.now());
        EventResponse expected = new EventResponse(1,"checkout","ERROR","payment timeout","t-1", Instant.now());
 when(svc.createEvent(request)).thenReturn(expected);
 mockMvc.perform(post("/api/v1/events").contentType(MediaType.APPLICATION_JSON).content(obj.writeValueAsString(request))).andExpect(status().isCreated());
  verify(svc).createEvent(request);

    }

    @Test
    void create_invalid_event() throws Exception {
        EventRequest request= new EventRequest("Payment","success","your payment is porcessed sucessfully","123", Instant.now().plus(Duration.ofHours(2)));

        mockMvc.perform(post("/api/v1/events").contentType(MediaType.APPLICATION_JSON).content(obj.writeValueAsString(request))).andExpect(status().isBadRequest());
        verifyNoInteractions(svc);

    }

}
