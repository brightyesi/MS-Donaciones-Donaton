package com.donaton.demo.ControllerTest;

import com.donaton.demo.Controller.DonacionController;
import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.DTO.DonacionResponseDTO;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.EstadoDonacion;
import com.donaton.demo.Service.DonacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DonacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DonacionService donacionService;

    @InjectMocks
    private DonacionController donacionController;

    // JavaTimeModule necesario porque LocalDate se serializa con jackson-datatype-jsr310
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private DonacionRequestDTO requestDTO;
    private DonacionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(donacionController)
                .build();

        // Campos inferidos del modelo Donacion — ajusta si tu RequestDTO difiere
        requestDTO = new DonacionRequestDTO();
        requestDTO.setRecurso("Arroz");
        requestDTO.setCategoria(CategoriaDonacion.ALIMENTO_NO_PERECIBLE);
        requestDTO.setCantidad(10);
        requestDTO.setUnidad("kg");
        requestDTO.setOrigen("Santiago");
        requestDTO.setDonadorId(1L);
        // centroAcopioId si tu RequestDTO lo requiere:
        // requestDTO.setCentroAcopioId(1L);

        // DonacionResponseDTO — fecha como String (serialización JSON de LocalDate)
        responseDTO = new DonacionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setRecurso("Arroz");
        responseDTO.setCategoria("ALIMENTO");
        responseDTO.setCantidad(10);
        responseDTO.setUnidad("kg");
        responseDTO.setOrigen("Santiago");
        responseDTO.setFecha("2026-05-11");
        responseDTO.setNombreCentroAcopio("Centro Norte");
        responseDTO.setEstado("RECIBIDA");    // estado por defecto según @PrePersist
    }

    // -----------------------------------------------------------------------
    // POST /api/donaciones
    // -----------------------------------------------------------------------

    // -----------------------------------------------------------------------
    // GET /api/donaciones/{id}
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("GET /api/donaciones/{id} - retorna donación existente con 200 OK")
    void obtenerPorId_existente_debeRetornar200() throws Exception {
        when(donacionService.obtenerPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/donaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.recurso", is("Arroz")))
                .andExpect(jsonPath("$.cantidad", is(10)))
                .andExpect(jsonPath("$.unidad", is("kg")))
                .andExpect(jsonPath("$.nombreCentroAcopio", is("Centro Norte")))
                .andExpect(jsonPath("$.fecha", is("2026-05-11")));

        verify(donacionService, times(1)).obtenerPorId(1L);
    }





    // -----------------------------------------------------------------------
    // GET /api/donaciones/estado/{estado}
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("GET /api/donaciones/estado/RECIBIDA - retorna donaciones recibidas")
    void listarPorEstado_recibida_debeRetornar200() throws Exception {
        when(donacionService.listarPorEstado(EstadoDonacion.RECIBIDA))
                .thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/donaciones/estado/RECIBIDA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].estado", is("RECIBIDA")));

        verify(donacionService, times(1)).listarPorEstado(EstadoDonacion.RECIBIDA);
    }

    @Test
    @DisplayName("GET /api/donaciones/estado/EN_BODEGA - retorna donaciones en bodega")
    void listarPorEstado_enBodega_debeRetornar200() throws Exception {
        DonacionResponseDTO enBodega = new DonacionResponseDTO();
        enBodega.setId(2L);
        enBodega.setRecurso("Frazadas");
        enBodega.setCategoria("ABRIGO");
        enBodega.setCantidad(5);
        enBodega.setUnidad("unidades");
        enBodega.setOrigen("Valparaíso");
        enBodega.setFecha("2026-05-11");
        enBodega.setNombreCentroAcopio("Centro Sur");
        enBodega.setEstado("EN_BODEGA");

        when(donacionService.listarPorEstado(EstadoDonacion.EN_BODEGA))
                .thenReturn(List.of(enBodega));

        mockMvc.perform(get("/api/donaciones/estado/EN_BODEGA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].estado", is("EN_BODEGA")));

        verify(donacionService, times(1)).listarPorEstado(EstadoDonacion.EN_BODEGA);
    }

    @Test
    @DisplayName("GET /api/donaciones/estado/DISTRIBUIDA - retorna donaciones distribuidas")
    void listarPorEstado_distribuida_debeRetornar200() throws Exception {
        DonacionResponseDTO distribuida = new DonacionResponseDTO();
        distribuida.setId(3L);
        distribuida.setRecurso("Leche");
        distribuida.setCategoria("ALIMENTO");
        distribuida.setCantidad(20);
        distribuida.setUnidad("litros");
        distribuida.setOrigen("Concepción");
        distribuida.setFecha("2026-05-11");
        distribuida.setNombreCentroAcopio("Centro Este");
        distribuida.setEstado("DISTRIBUIDA");

        when(donacionService.listarPorEstado(EstadoDonacion.DISTRIBUIDA))
                .thenReturn(List.of(distribuida));

        mockMvc.perform(get("/api/donaciones/estado/DISTRIBUIDA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].estado", is("DISTRIBUIDA")));

        verify(donacionService, times(1)).listarPorEstado(EstadoDonacion.DISTRIBUIDA);
    }

    @Test
    @DisplayName("GET /api/donaciones/estado/{estado} - estado sin donaciones retorna lista vacía")
    void listarPorEstado_sinResultados_debeRetornarListaVacia() throws Exception {
        when(donacionService.listarPorEstado(any(EstadoDonacion.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/donaciones/estado/DISTRIBUIDA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // -----------------------------------------------------------------------
    // GET /api/donaciones/usuario/{donadorId}
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("GET /api/donaciones/usuario/{donadorId} - retorna donaciones del usuario con 200 OK")
    void listarPorUsuario_conResultados_debeRetornar200() throws Exception {
        when(donacionService.listarPorDonador(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/donaciones/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].origen", is("Santiago")))
                .andExpect(jsonPath("$[0].nombreCentroAcopio", is("Centro Norte")))
                .andExpect(jsonPath("$[0].estado", is("RECIBIDA")));

        verify(donacionService, times(1)).listarPorDonador(1L);
    }

    @Test
    @DisplayName("GET /api/donaciones/usuario/{donadorId} - usuario sin donaciones retorna lista vacía")
    void listarPorUsuario_sinResultados_debeRetornarListaVacia() throws Exception {
        when(donacionService.listarPorDonador(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/donaciones/usuario/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}