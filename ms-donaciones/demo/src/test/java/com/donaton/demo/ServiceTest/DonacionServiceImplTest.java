package com.donaton.demo.ServiceTest;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.DTO.DonacionResponseDTO;
import com.donaton.demo.Exception.DonacionNotFoundException;
import com.donaton.demo.Factory.DonacionFactory;
import com.donaton.demo.Factory.DonacionFactoryProvider;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.CentroAcopio;
import com.donaton.demo.Model.Donacion;
import com.donaton.demo.Model.EstadoDonacion;
import com.donaton.demo.Repository.CentroAcopioRepository;
import com.donaton.demo.Repository.DonacionRepository;
import com.donaton.demo.Service.DonacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonacionServiceImplTest {

    // ── Mocks (simulan la BD, no la tocan) ──────────────────────────────────
    @Mock
    private DonacionRepository donacionRepository;

    @Mock
    private CentroAcopioRepository centroRepository;

    // ── El service real con los mocks inyectados ─────────────────────────────
    @InjectMocks
    private DonacionServiceImpl donacionService;

    // ── Objetos reutilizables en cada test ───────────────────────────────────
    private CentroAcopio centroAcopio;
    private Donacion donacion;
    private DonacionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Centro de acopio de prueba
        centroAcopio = new CentroAcopio();
        centroAcopio.setIdCentro(1L);
        centroAcopio.setNombre("Centro Santiago");
        centroAcopio.setDireccion("Av. Principal 123");
        centroAcopio.setRegion("Metropolitana");
        centroAcopio.setActivo(true);

        // Donacion de prueba
        donacion = new Donacion();
        donacion.setIdDonacion(1L);
        donacion.setRecurso("Frazadas");
        donacion.setCategoria(CategoriaDonacion.ROPA);
        donacion.setCantidad(10);
        donacion.setUnidad("unidades");
        donacion.setOrigen("Santiago");
        donacion.setFecha(LocalDate.now());
        donacion.setEstado(EstadoDonacion.RECIBIDA);
        donacion.setCentroAcopio(centroAcopio);
        donacion.setDonadorId(42L);

        // DTO de entrada
        requestDTO = new DonacionRequestDTO();
        requestDTO.setRecurso("Frazadas");
        requestDTO.setCategoria(CategoriaDonacion.ROPA);
        requestDTO.setCantidad(10);
        requestDTO.setUnidad("unidades");
        requestDTO.setOrigen("Santiago");
        requestDTO.setCentroAcopioId(1L);
        requestDTO.setDonadorId(42L);
    }

    // ════════════════════════════════════════════════════════════════════════
    // crearDonacion
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("crearDonacion → debe guardar y retornar el DTO correctamente")
    void crearDonacion_exitoso() {
        // Arrange: qué devuelven los mocks cuando el service los llame
        when(centroRepository.findById(1L)).thenReturn(Optional.of(centroAcopio));

        // Mockear el DonacionFactoryProvider (clase estática)
        DonacionFactory factoryMock = mock(DonacionFactory.class);
        when(factoryMock.crear(requestDTO)).thenReturn(donacion);

        try (MockedStatic<DonacionFactoryProvider> staticMock =
                     mockStatic(DonacionFactoryProvider.class)) {

            staticMock.when(() -> DonacionFactoryProvider.getFactory(CategoriaDonacion.ROPA))
                    .thenReturn(factoryMock);

            when(donacionRepository.save(any(Donacion.class))).thenReturn(donacion);

            // Act
            DonacionResponseDTO resultado = donacionService.crearDonacion(requestDTO);

            // Assert
            assertThat(resultado).isNotNull();
            assertThat(resultado.getRecurso()).isEqualTo("Frazadas");
            assertThat(resultado.getCategoria()).isEqualTo("ROPA");
            assertThat(resultado.getCantidad()).isEqualTo(10);
            assertThat(resultado.getNombreCentroAcopio()).isEqualTo("Centro Santiago");
            assertThat(resultado.getEstado()).isEqualTo("RECIBIDA");

            // Verificar que sí se llamó a save
            verify(donacionRepository, times(1)).save(any(Donacion.class));
        }
    }

    @Test
    @DisplayName("crearDonacion → lanza excepción si el centro de acopio no existe")
    void crearDonacion_centroNoExiste_lanzaExcepcion() {
        // Arrange: el centro no está en la BD
        when(centroRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> donacionService.crearDonacion(requestDTO))
                .isInstanceOf(DonacionNotFoundException.class)
                .hasMessageContaining("Centro de acopio no encontrado con id 1");

        // Verificar que nunca se intentó guardar una donacion
        verify(donacionRepository, never()).save(any());
    }

    // ════════════════════════════════════════════════════════════════════════
    // listarTodas
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("listarTodas → retorna lista con todos los elementos")
    void listarTodas_retornaLista() {
        // Arrange
        Donacion donacion2 = new Donacion();
        donacion2.setIdDonacion(2L);
        donacion2.setRecurso("Arroz");
        donacion2.setCategoria(CategoriaDonacion.ALIMENTO_NO_PERECIBLE);
        donacion2.setCantidad(5);
        donacion2.setUnidad("kg");
        donacion2.setOrigen("Valparaíso");
        donacion2.setFecha(LocalDate.now());
        donacion2.setEstado(EstadoDonacion.EN_BODEGA);
        donacion2.setCentroAcopio(centroAcopio);

        when(donacionRepository.findAll()).thenReturn(List.of(donacion, donacion2));

        // Act
        List<DonacionResponseDTO> resultado = donacionService.listarTodas();

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getRecurso()).isEqualTo("Frazadas");
        assertThat(resultado.get(1).getRecurso()).isEqualTo("Arroz");
    }

    @Test
    @DisplayName("listarTodas → retorna lista vacía si no hay donaciones")
    void listarTodas_listaVacia() {
        when(donacionRepository.findAll()).thenReturn(List.of());

        List<DonacionResponseDTO> resultado = donacionService.listarTodas();

        assertThat(resultado).isEmpty();
    }

    // ════════════════════════════════════════════════════════════════════════
    // obtenerPorId
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("obtenerPorId → retorna DTO cuando existe la donacion")
    void obtenerPorId_existe() {
        when(donacionRepository.findById(1L)).thenReturn(Optional.of(donacion));

        DonacionResponseDTO resultado = donacionService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getRecurso()).isEqualTo("Frazadas");
    }

    @Test
    @DisplayName("obtenerPorId → lanza excepción cuando NO existe la donacion")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(donacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> donacionService.obtenerPorId(99L))
                .isInstanceOf(DonacionNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ════════════════════════════════════════════════════════════════════════
    // listarPorCategoria
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("listarPorCategoria → retorna solo las donaciones de esa categoría")
    void listarPorCategoria_retornaFiltrado() {
        when(donacionRepository.findByCategoria(CategoriaDonacion.ROPA))
                .thenReturn(List.of(donacion));

        List<DonacionResponseDTO> resultado =
                donacionService.listarPorCategoria(CategoriaDonacion.ROPA);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCategoria()).isEqualTo("ROPA");
    }

    @Test
    @DisplayName("listarPorCategoria → retorna vacío si no hay donaciones de esa categoría")
    void listarPorCategoria_sinResultados() {
        when(donacionRepository.findByCategoria(CategoriaDonacion.ALIMENTO_NO_PERECIBLE))
                .thenReturn(List.of());

        List<DonacionResponseDTO> resultado =
                donacionService.listarPorCategoria(CategoriaDonacion.ALIMENTO_NO_PERECIBLE);

        assertThat(resultado).isEmpty();
    }

    // ════════════════════════════════════════════════════════════════════════
    // listarPorEstado
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("listarPorEstado → retorna solo las donaciones con ese estado")
    void listarPorEstado_retornaFiltrado() {
        when(donacionRepository.findByEstado(EstadoDonacion.RECIBIDA))
                .thenReturn(List.of(donacion));

        List<DonacionResponseDTO> resultado =
                donacionService.listarPorEstado(EstadoDonacion.RECIBIDA);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado()).isEqualTo("RECIBIDA");
    }

    // ════════════════════════════════════════════════════════════════════════
    // listarPorDonador
    // ════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("listarPorDonador → retorna donaciones del donador indicado")
    void listarPorDonador_retornaFiltrado() {
        when(donacionRepository.findByDonadorId(42L)).thenReturn(List.of(donacion));

        List<DonacionResponseDTO> resultado = donacionService.listarPorDonador(42L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getRecurso()).isEqualTo("Frazadas");
    }

    @Test
    @DisplayName("listarPorDonador → retorna vacío si el donador no tiene donaciones")
    void listarPorDonador_sinResultados() {
        when(donacionRepository.findByDonadorId(999L)).thenReturn(List.of());

        List<DonacionResponseDTO> resultado = donacionService.listarPorDonador(999L);

        assertThat(resultado).isEmpty();
    }
}
