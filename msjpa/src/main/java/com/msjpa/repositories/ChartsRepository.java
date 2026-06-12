package com.msjpa.repositories;

import com.msjpa.models.RDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChartsRepository extends JpaRepository<RDV, Integer> {

    @Query(value = "CALL spRdvList(:idUser, :annee)", nativeQuery = true)
    List<Object[]> callSpRdvList(@Param("idUser") Integer idUser, @Param("annee") int annee);
}