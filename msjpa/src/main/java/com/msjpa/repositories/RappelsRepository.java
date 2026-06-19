package com.msjpa.repositories;

import com.msjpa.models.Rappels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RappelsRepository extends JpaRepository<Rappels, Integer> {

    List<Rappels> findByRdvId(Integer rdvId);

}