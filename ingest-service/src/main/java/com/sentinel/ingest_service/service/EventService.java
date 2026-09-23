package com.sentinel.ingest_service.service;

import com.sentinel.ingest_service.dto.EventRequest;
import com.sentinel.ingest_service.entity.Event;
import com.sentinel.ingest_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventService {

    private static final int MAX_PAGE_SIZE = 200;

    @Autowired
    EventRepository eventRepository;

    public Event mapper(EventRequest req) {
        Event e = new Event();
        e.setService(req.service());
        e.setLevel(req.level());
        e.setMessage(req.message());
        e.setTraceId(req.traceId());
        e.setOccurredAt(req.occurredAt());
        e.setReceivedAt(Instant.now());
        return e;
    }

    public Event createEvent(EventRequest req) {
        return eventRepository.save(mapper(req));
    }

    public Page<Event> listEvents(String svcName, int page, int size) {
        int cappedSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageReq = PageRequest.of(Math.max(page, 0), cappedSize,
                Sort.by(Sort.Direction.DESC, "occurredAt"));
        if(svcName==null){
            return eventRepository.findAll( pageReq);
        }
        return eventRepository.findByService(svcName, pageReq);
    }
}
