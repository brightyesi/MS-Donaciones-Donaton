package com.donaton.demo.Repository;

import com.donaton.demo.Model.CentroAcopio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentroAcopioRepository extends JpaRepository<CentroAcopio,Long> {

    List<CentroAcopio>findByRegion(String region);
    List<CentroAcopio>findByActivoTrue();

}
