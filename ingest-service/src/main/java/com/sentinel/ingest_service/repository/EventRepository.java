package com.sentinel.ingest_service.repository;

import com.sentinel.ingest_service.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer > {


   Page<Event> findByService(String service,Pageable page);

}
