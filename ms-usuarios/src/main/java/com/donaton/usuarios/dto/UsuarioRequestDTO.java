package com.donaton.usuarios.dto;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String username;
    private String email;
    private String password;
    private String rol;
}