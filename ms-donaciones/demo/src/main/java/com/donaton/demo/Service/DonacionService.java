package com.donaton.demo.Service;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.DTO.DonacionResponseDTO;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.EstadoDonacion;

import java.util.List;

public interface DonacionService {
    DonacionResponseDTO crearDonacion(DonacionRequestDTO dto);
    List<DonacionResponseDTO> listarTodas();
    DonacionResponseDTO obtenerPorId(Long id);
    DonacionResponseDTO actualizarEstadoDonacion(Long id, String nuevoEstado);
    List<DonacionResponseDTO> listarPorCategoria(CategoriaDonacion categoria);
    List<DonacionResponseDTO> listarPorEstado(EstadoDonacion estado);
    List<DonacionResponseDTO> listarPorDonador(Long donadorId);
}
