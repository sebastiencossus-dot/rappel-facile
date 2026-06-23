package com.msstats.repositories;

import com.msstats.models.VisiteEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VisiteRepository extends MongoRepository<VisiteEvent, String> {
    List<VisiteEvent> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    long countByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<VisiteEvent> findByUserEmailAndTimestampBetween(String email, LocalDateTime start, LocalDateTime end);
}