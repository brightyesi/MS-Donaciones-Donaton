package com.donaton.usuarios.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String rol;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
}