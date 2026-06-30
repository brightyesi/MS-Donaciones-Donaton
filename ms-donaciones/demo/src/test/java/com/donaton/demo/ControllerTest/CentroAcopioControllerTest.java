package com.donaton.demo.ControllerTest;

import com.donaton.demo.Controller.CentroAcopioController;
import com.donaton.demo.DTO.AcopioRequestDTO;
import com.donaton.demo.DTO.AcopioResponseDTO;
import com.donaton.demo.Service.CentroAcopioService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CentroAcopioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CentroAcopioService centroAcopioService;

    @InjectMocks
    private CentroAcopioController centroAcopioController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private AcopioRequestDTO requestDTO;
    private AcopioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // Standalone: sin Spring context, sin BD, sin MySQL
        mockMvc = MockMvcBuilders
                .standaloneSetup(centroAcopioController)
                .build();

        // Ajusta los campos según los atributos reales de tus DTOs
        requestDTO = new AcopioRequestDTO();
        requestDTO.setNombre("Centro Norte");
        requestDTO.setRegion("Metropolitana");
        requestDTO.setDireccion("Av. Siempre Viva 123");
        requestDTO.setActivo(true);

        responseDTO = new AcopioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Centro Norte");
        responseDTO.setRegion("Metropolitana");
        responseDTO.setDireccion("Av. Siempre Viva 123");
        responseDTO.setActivo(true);
    }

    // -----------------------------------------------------------------------
    // POST /api/centros
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("POST /api/centros - crea un centro y retorna 201 CREATED")
    void crear_debeRetornar201YElCentroCreado() throws Exception {
        when(centroAcopioService.crear(any(AcopioRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/centros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Centro Norte")))
                .andExpect(jsonPath("$.region", is("Metropolitana")));

        verify(centroAcopioService, times(1)).crear(any(AcopioRequestDTO.class));
    }

    // -----------------------------------------------------------------------
    // GET /api/centros
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("GET /api/centros - retorna lista con 200 OK")
    void listar_debeRetornar200YLista() throws Exception {
        when(centroAcopioService.listar()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/centros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Centro Norte")));

        verify(centroAcopioService, times(1)).listar();
    }

    @Test
    @DisplayName("GET /api/centros - retorna lista vacía con 200 OK")
    void listar_sinCentros_debeRetornarListaVacia() throws Exception {
        when(centroAcopioService.listar()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/centros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // -----------------------------------------------------------------------
    // GET /api/centros/{id}
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("GET /api/centros/{id} - retorna el centro con 200 OK")
    void obtenerPorId_existente_debeRetornar200() throws Exception {
        when(centroAcopioService.obtenerPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/centros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Centro Norte")));

        verify(centroAcopioService, times(1)).obtenerPorId(1L);
    }

    // -----------------------------------------------------------------------
    // GET /api/centros/region/{region}
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("GET /api/centros/region/{region} - retorna centros de la región con 200 OK")
    void listarPorRegion_conResultados_debeRetornar200() throws Exception {
        when(centroAcopioService.listarPorRegion("Metropolitana")).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/centros/region/Metropolitana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].region", is("Metropolitana")));

        verify(centroAcopioService, times(1)).listarPorRegion("Metropolitana");
    }

    @Test
    @DisplayName("GET /api/centros/region/{region} - región sin centros retorna lista vacía")
    void listarPorRegion_sinResultados_debeRetornarListaVacia() throws Exception {
        when(centroAcopioService.listarPorRegion(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/centros/region/Antofagasta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // -----------------------------------------------------------------------
    // GET /api/centros/activos
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("GET /api/centros/activos - retorna centros activos con 200 OK")
    void listarActivos_conResultados_debeRetornar200() throws Exception {
        when(centroAcopioService.listarActivo()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/centros/activos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].activo", is(true)));

        verify(centroAcopioService, times(1)).listarActivo();
    }

    @Test
    @DisplayName("GET /api/centros/activos - sin centros activos retorna lista vacía")
    void listarActivos_sinResultados_debeRetornarListaVacia() throws Exception {
        when(centroAcopioService.listarActivo()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/centros/activos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}