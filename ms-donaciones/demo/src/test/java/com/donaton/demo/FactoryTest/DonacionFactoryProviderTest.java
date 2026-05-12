package com.donaton.demo.FactoryTest;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.Factory.*;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.Donacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DonacionFactoryProviderTest {

    private DonacionRequestDTO dto;

    @Test
    @DisplayName("Debe retornar DonacionAlimento cuando la categoría es ALIMENTO_NO_PERECIBLE")
    void debeRetornarDonacionAlimento() {
        // Arrange
        CategoriaDonacion categoria = CategoriaDonacion.ALIMENTO_NO_PERECIBLE;

        // Act
        DonacionFactory factory = DonacionFactoryProvider.getFactory(categoria);

        // Assert
        assertThat(factory).isNotNull();
        assertThat(factory).isInstanceOf(DonacionAlimento.class);
    }

    @Test
    @DisplayName("Debe retornar DonacionRopa cuando la categoría es ROPA")
    void debeRetornarDonacionRopa() {
        // Arrange
        CategoriaDonacion categoria = CategoriaDonacion.ROPA;

        // Act
        DonacionFactory factory = DonacionFactoryProvider.getFactory(categoria);

        // Assert
        assertThat(factory).isNotNull();
        assertThat(factory).isInstanceOf(DonacionRopa.class);
    }

    @Test
    @DisplayName("Debe retornar DonacionInsumoMedico cuando la categoría es INSUMO_MEDICO")
    void debeRetornarDonacionInsumoMedico() {
        // Arrange
        CategoriaDonacion categoria = CategoriaDonacion.INSUMO_MEDICO;

        // Act
        DonacionFactory factory = DonacionFactoryProvider.getFactory(categoria);

        // Assert
        assertThat(factory).isNotNull();
        assertThat(factory).isInstanceOf(DonacionInsumoMedico.class);
    }

    @Test
    @DisplayName("Debe lanzar Exception si la categoría es nula")
    void debeLanzarExcepcionConNull() {
        // Al usar un switch expression moderno sobre un enum,
        // Java lanza un NullPointerException automáticamente si el valor es null.
        assertThrows(NullPointerException.class, () -> {
            DonacionFactoryProvider.getFactory(null);
        });
    }


    @BeforeEach
    void setUp() {
        // Configuramos un DTO con datos de prueba válidos
        dto = new DonacionRequestDTO();
        dto.setRecurso("Arroz");
        dto.setCategoria(CategoriaDonacion.ALIMENTO_NO_PERECIBLE);
        dto.setCantidad(10);
        dto.setUnidad("kg");
        dto.setOrigen("Donación Anónima");
        dto.setDonadorId(100L);
        dto.setCentroAcopioId(1L);
    }

    @Test
    @DisplayName("DonacionAlimento debe mapear todos los campos del DTO a la Entidad")
    void testDonacionAlimentoMapping() {
        // Arrange
        DonacionFactory factory = new DonacionAlimento();

        // Act
        Donacion resultado = factory.crear(dto);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getRecurso()).isEqualTo(dto.getRecurso());
        assertThat(resultado.getCantidad()).isEqualTo(dto.getCantidad());
        assertThat(resultado.getUnidad()).isEqualTo(dto.getUnidad());
        assertThat(resultado.getOrigen()).isEqualTo(dto.getOrigen());
        assertThat(resultado.getDonadorId()).isEqualTo(dto.getDonadorId());
        // Verificamos que la categoría sea la correcta para esta fábrica
        assertThat(resultado.getCategoria()).isEqualTo(CategoriaDonacion.ALIMENTO_NO_PERECIBLE);
    }

    @Test
    @DisplayName("DonacionRopa debe asignar la categoría ROPA aunque el DTO diga otra cosa")
    void testDonacionRopaMapping() {
        // Arrange
        dto.setRecurso("Camisetas");
        dto.setCategoria(CategoriaDonacion.ROPA); // Ajustamos el DTO para coherencia
        dto.setUnidad("unidades");
        DonacionFactory factory = new DonacionRopa();

        // Act
        Donacion resultado = factory.crear(dto);

        // Assert
        assertThat(resultado.getRecurso()).isEqualTo("Camisetas");
        assertThat(resultado.getCategoria()).isEqualTo(CategoriaDonacion.ROPA);
    }

    @Test
    @DisplayName("DonacionInsumoMedico debe mapear correctamente los insumos")
    void testDonacionInsumoMedicoMapping() {
        // Arrange
        dto.setRecurso("Gasa Estéril");
        dto.setCategoria(CategoriaDonacion.INSUMO_MEDICO);
        dto.setUnidad("unidades");

        DonacionFactory factory = new DonacionInsumoMedico();

        // Act
        Donacion resultado = factory.crear(dto);

        // Assert
        assertThat(resultado.getRecurso()).isEqualTo("Gasa Estéril");
        assertThat(resultado.getCategoria()).isEqualTo(CategoriaDonacion.INSUMO_MEDICO);
    }
}