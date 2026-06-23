package com.msstats.repositories;

import com.msstats.models.ConnexionEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ConnexionRepository extends MongoRepository<ConnexionEvent, String> {
    List<ConnexionEvent> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    long countByTimestampBetween(LocalDateTime start, LocalDateTime end);
    long countByUserEmailAndTimestampBetween(String email, LocalDateTime start, LocalDateTime end);
}