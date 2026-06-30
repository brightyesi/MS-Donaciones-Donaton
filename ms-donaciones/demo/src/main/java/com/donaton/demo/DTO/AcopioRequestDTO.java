package com.donaton.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor@NoArgsConstructor
public class AcopioRequestDTO {
    @NotBlank(message = "El nombre del centro es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La región es obligatoria")
    private String region;

    @NotNull(message = "El estado activo/inactivo es obligatorio")
    private Boolean activo;
}
