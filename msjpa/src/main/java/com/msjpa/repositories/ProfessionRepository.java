package com.msjpa.repositories;

import org.springframework.stereotype.Repository;


import com.msjpa.models.Professions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProfessionRepository extends JpaRepository<Professions, Integer> {


}
