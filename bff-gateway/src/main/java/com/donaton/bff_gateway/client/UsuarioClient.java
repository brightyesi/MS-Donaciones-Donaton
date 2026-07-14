package com.donaton.bff_gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioClient {

    private final RestTemplate restTemplate;

    @Value("${ms.usuarios.url}")
    private String usuariosUrl;

    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String obtenerServicioUsuarios() {

        return restTemplate.getForObject(
                usuariosUrl,
                String.class
        );
    }
}