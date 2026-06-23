package com.msstats.repositories;

import com.msstats.models.RdvStatEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RdvStatRepository extends MongoRepository<RdvStatEvent, String> {
    List<RdvStatEvent> findByUserEmail(String userEmail);
    List<RdvStatEvent> findByNbAlertesGreaterThan(Integer nb);
}