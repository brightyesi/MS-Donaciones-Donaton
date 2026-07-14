package com.donaton.demo.Service;

import com.donaton.demo.DTO.AcopioRequestDTO;
import com.donaton.demo.DTO.AcopioResponseDTO;
import com.donaton.demo.Exception.DonacionNotFoundException;
import com.donaton.demo.Model.CentroAcopio;
import com.donaton.demo.Repository.CentroAcopioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CentroAcopioServiceImpl implements CentroAcopioService{

    private final CentroAcopioRepository acopioRepository;

    public CentroAcopioServiceImpl(CentroAcopioRepository centroAcopioRepository){
        this.acopioRepository= centroAcopioRepository;
    }

    @Override
    public AcopioResponseDTO crear (AcopioRequestDTO requestDTO){
        CentroAcopio centroAcopio =new  CentroAcopio();
        centroAcopio.setNombre(requestDTO.getNombre());
        centroAcopio.setDireccion(requestDTO.getDireccion());
        centroAcopio.setRegion(requestDTO.getRegion());
        centroAcopio.setActivo(requestDTO.getActivo());

        CentroAcopio saved= acopioRepository.save(centroAcopio);
        return toResponse(saved);
    }

    @Override
    public List<AcopioResponseDTO> listar() {
        return acopioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AcopioResponseDTO obtenerPorId(Long id){
        CentroAcopio centro = acopioRepository.findById(id)
                .orElseThrow(()-> new DonacionNotFoundException("Centro de acopio no encontrado con id: " + id));
        return toResponse(centro);
    }

    @Override
    public List<AcopioResponseDTO> listarActivo() {
        return acopioRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<AcopioResponseDTO>listarPorRegion(String region){
        return acopioRepository.findByRegion(region)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    private AcopioResponseDTO toResponse(CentroAcopio c) {
        AcopioResponseDTO dto = new AcopioResponseDTO();
        dto.setId(c.getIdCentro());
        dto.setNombre(c.getNombre());
        dto.setDireccion(c.getDireccion());
        dto.setRegion(c.getRegion());
        dto.setActivo(c.isActivo());
        return dto;
    }
}


