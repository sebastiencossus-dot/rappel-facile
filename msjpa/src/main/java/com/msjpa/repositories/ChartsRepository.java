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

    @Query(value = """
    SELECT r.*
    FROM rdv r
    JOIN professions pr ON r.professions_id = pr.id
    JOIN categories c ON pr.categories_id = c.id
    WHERE c.name = :categorie
      AND MONTH(r.date_rdv) = :mois
      AND r.user_id = :userId
""", nativeQuery = true)
    List<RDV> findByCategorieAndMonth(
            @Param("categorie") String categorie,
            @Param("mois") int mois,
            @Param("userId") int userId
    );

    @Query(value = """
    SELECT r.*
    FROM rdv r
    WHERE r.isok = :statut
      AND MONTH(r.date_rdv) = :mois
      AND r.user_id = :userId
""", nativeQuery = true)
    List<RDV> findByStatutAndMonth(
            @Param("statut") int statut,
            @Param("mois") int mois,
            @Param("userId") int userId
    );

}