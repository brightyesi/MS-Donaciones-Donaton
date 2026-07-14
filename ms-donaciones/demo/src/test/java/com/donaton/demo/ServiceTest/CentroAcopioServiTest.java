package com.donaton.demo.ServiceTest;


import com.donaton.demo.DTO.AcopioRequestDTO;
import com.donaton.demo.DTO.AcopioResponseDTO;
import com.donaton.demo.Exception.DonacionNotFoundException;
import com.donaton.demo.Model.CentroAcopio;
import com.donaton.demo.Repository.CentroAcopioRepository;
import com.donaton.demo.Service.CentroAcopioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CentroAcopioServiTest {

    @Mock
    private CentroAcopioRepository centroAcopioRepository;

    @InjectMocks
    private CentroAcopioServiceImpl centroAcopioService;

    private CentroAcopio centroActivo;
    private CentroAcopio centroInactivo;
    private AcopioRequestDTO requestDTO;

    @BeforeEach
    void setUp(){
        centroActivo = new CentroAcopio();
        centroActivo.setIdCentro(1L);
        centroActivo.setNombre("Centro Santiago");
        centroActivo.setDireccion("Av. Principal 123");
        centroActivo.setRegion("Metropolitana");
        centroActivo.setActivo(true);

        centroInactivo = new CentroAcopio();
        centroInactivo.setIdCentro(2L);
        centroInactivo.setNombre("Centro Cerrado");
        centroInactivo.setDireccion("Calle Vieja 456");
        centroInactivo.setRegion("Metropolitana");
        centroInactivo.setActivo(false);

        requestDTO = new AcopioRequestDTO();
        requestDTO.setNombre("Centro Santiago");
        requestDTO.setDireccion("Av. Principal 123");
        requestDTO.setRegion("Metropolitana");
        requestDTO.setActivo(true);
    }

    @Test
    @DisplayName("listar → retorna todos los centros")
    void listar_retornaLista() {
        when(centroAcopioRepository.findAll()).thenReturn(List.of(centroActivo, centroInactivo));

        List<AcopioResponseDTO> resultado = centroAcopioService.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Centro Santiago");
        assertThat(resultado.get(1).getNombre()).isEqualTo("Centro Cerrado");
    }

    @Test
    @DisplayName("listar → retorna lista vacía si no hay centros")
    void listar_listaVacia() {
        when(centroAcopioRepository.findAll()).thenReturn(List.of());

        List<AcopioResponseDTO> resultado = centroAcopioService.listar();

        assertThat(resultado).isEmpty();
    }

    // ════════════════════════════════════════════════════════════════════════
    // obtenerPorId
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("obtenerPorId → retorna el DTO cuando existe")
    void obtenerPorId_existe() {
        when(centroAcopioRepository.findById(1L)).thenReturn(Optional.of(centroActivo));

        AcopioResponseDTO resultado = centroAcopioService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Centro Santiago");
    }

    @Test
    @DisplayName("obtenerPorId → lanza excepción cuando NO existe")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(centroAcopioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> centroAcopioService.obtenerPorId(99L))
                .isInstanceOf(DonacionNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ════════════════════════════════════════════════════════════════════════
    // listarPorRegion
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("listarPorRegion → retorna centros de esa región")
    void listarPorRegion_conResultados() {
        when(centroAcopioRepository.findByRegion("Metropolitana"))
                .thenReturn(List.of(centroActivo, centroInactivo));

        List<AcopioResponseDTO> resultado =
                centroAcopioService.listarPorRegion("Metropolitana");

        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(c -> c.getRegion().equals("Metropolitana"));
    }

    @Test
    @DisplayName("listarPorRegion → retorna vacío si no hay centros en esa región")
    void listarPorRegion_sinResultados() {
        when(centroAcopioRepository.findByRegion("Atacama")).thenReturn(List.of());

        List<AcopioResponseDTO> resultado =
                centroAcopioService.listarPorRegion("Atacama");

        assertThat(resultado).isEmpty();
    }

    // ════════════════════════════════════════════════════════════════════════
    // listarActivo
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("listarActivo → retorna solo los centros activos")
    void listarActivo_retornaSoloActivos() {
        when(centroAcopioRepository.findByActivoTrue()).thenReturn(List.of(centroActivo));

        List<AcopioResponseDTO> resultado = centroAcopioService.listarActivo();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getActivo()).isTrue();
        assertThat(resultado.get(0).getNombre()).isEqualTo("Centro Santiago");
    }

    @Test
    @DisplayName("listarActivo → retorna vacío si ningún centro está activo")
    void listarActivo_sinActivos() {
        when(centroAcopioRepository.findByActivoTrue()).thenReturn(List.of());

        List<AcopioResponseDTO> resultado = centroAcopioService.listarActivo();

        assertThat(resultado).isEmpty();
    }



}
