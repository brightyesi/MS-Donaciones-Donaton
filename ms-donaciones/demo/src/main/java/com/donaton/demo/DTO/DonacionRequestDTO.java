package com.donaton.demo.DTO;

import com.donaton.demo.Model.CategoriaDonacion;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class DonacionRequestDTO {

    @NotBlank(message = "El recurso es obligatorio")
    private String recurso;
    @NotNull(message = "La categoría no puede ser nula")
    private CategoriaDonacion categoria;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    private String unidad;
    private String origen;

    @NotNull(message = "Debe asignar un centro de acopio")
    private Long centroAcopioId;

    @NotNull(message = "El ID del donador es obligatorio")
    private Long donadorId;
}
