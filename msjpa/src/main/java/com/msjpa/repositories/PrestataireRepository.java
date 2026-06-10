package com.msjpa.repositories;

import com.msjpa.models.Prestataires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrestataireRepository extends JpaRepository<Prestataires, Integer> {

    @Query("""
    SELECT DISTINCT p FROM Prestataires p
    LEFT JOIN FETCH p.exercices e
    LEFT JOIN FETCH e.professions prof
    LEFT JOIN FETCH prof.categorie
""")
    List<Prestataires> findAllWithExercices();

    @Query("""
    SELECT DISTINCT p FROM Prestataires p
    LEFT JOIN FETCH p.locals l
    LEFT JOIN FETCH l.adresses
""")
    List<Prestataires> findAllWithLocals();
}
