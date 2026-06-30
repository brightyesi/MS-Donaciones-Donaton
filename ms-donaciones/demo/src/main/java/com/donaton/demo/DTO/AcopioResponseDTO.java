package com.donaton.demo.DTO;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcopioResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String region;
    private Boolean activo;
}
