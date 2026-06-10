package com.msjpa.repositories;



import com.msjpa.models.RDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdvRepository extends JpaRepository<RDV, Integer> {

    @Query("""
    SELECT r FROM RDV r
    LEFT JOIN FETCH r.rappels
    LEFT JOIN FETCH r.professions
    LEFT JOIN FETCH r.prestataires
    LEFT JOIN FETCH r.adresses
    WHERE r.user.id = :userId
    """)
    List<RDV> findFullByUserId(@Param("userId") Integer userId);
    List<RDV> findByUser_Email(String email);
}
