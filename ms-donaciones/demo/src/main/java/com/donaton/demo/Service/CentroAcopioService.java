package com.donaton.demo.Service;

import com.donaton.demo.DTO.AcopioRequestDTO;
import com.donaton.demo.DTO.AcopioResponseDTO;

import java.util.List;

public interface CentroAcopioService {

    AcopioResponseDTO crear(AcopioRequestDTO request);
    List<AcopioResponseDTO>listar();
    AcopioResponseDTO obtenerPorId (Long id);
    List<AcopioResponseDTO> listarPorRegion(String region);
    List<AcopioResponseDTO> listarActivo();


}
