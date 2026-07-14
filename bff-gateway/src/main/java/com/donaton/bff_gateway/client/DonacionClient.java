package com.donaton.bff_gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DonacionClient {

    private final RestTemplate restTemplate;

    @Value("${ms.donaciones.url}")
    private String donacionesUrl;

    public DonacionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String obtenerServicioDonaciones() {

        return restTemplate.getForObject(
                donacionesUrl,
                String.class
        );
    }
}