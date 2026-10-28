package com.sentinel.ingest_service.service;

import com.sentinel.ingest_service.dto.EventRequest;
import com.sentinel.ingest_service.dto.EventResponse;
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

   private   final EventRepository eventRepository;
   public EventService( EventRepository eventRepository){
       this.eventRepository=eventRepository;

   }

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

    public EventResponse toEventResponse(Event e){
       return new EventResponse(e.getId(),e.getService(),e.getLevel(),e.getMessage(),e.getTraceId(),e.getOccurredAt());
    }

    public EventResponse createEvent(EventRequest req) {

       Event e =eventRepository.save(mapper(req));
       return toEventResponse(e) ;
    }

    public Page<EventResponse> listEvents(String svcName, int page, int size) {
        int cappedSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        Pageable pageReq = PageRequest.of(Math.max(page, 0), cappedSize,
                Sort.by(Sort.Direction.DESC, "occurredAt"));
        Page<Event> events=(svcName==null) ? eventRepository.findAll( pageReq):eventRepository.findByService(svcName, pageReq);

        return events.map(this::toEventResponse);

    }
}
