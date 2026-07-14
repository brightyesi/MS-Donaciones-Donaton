package com.donaton.demo.Controller;

import com.donaton.demo.DTO.AcopioRequestDTO;
import com.donaton.demo.DTO.AcopioResponseDTO;
import com.donaton.demo.Service.CentroAcopioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centros")
@Tag(name = "Centro de acopio", description = "Gestion de centros de acopio")
public class CentroAcopioController {

    private final CentroAcopioService centroAcopioService;

    public CentroAcopioController(CentroAcopioService centroAcopioService){
        this.centroAcopioService =centroAcopioService;
    }

    @Operation(summary = "crear un centro de acopio")
    @PostMapping
    public ResponseEntity<AcopioResponseDTO> crear(@Valid @RequestBody AcopioRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(centroAcopioService.crear(request));
    }

    @Operation(summary = "Listar todo los  centro de acopio")
    @GetMapping
    public ResponseEntity<List<AcopioResponseDTO>> listar() {
        return ResponseEntity.ok(centroAcopioService.listar());
    }

    @Operation(summary = "Obterner el centro de acopio por id")
    @GetMapping("/{id}")
    private ResponseEntity<AcopioResponseDTO>obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(centroAcopioService.obtenerPorId(id));
    }

    @Operation(summary = "Listar centros de acopio por región")
    @GetMapping("/region/{region}")
    public ResponseEntity<List<AcopioResponseDTO>>listarPorRegion(@PathVariable String region){
        return ResponseEntity.ok(centroAcopioService.listarPorRegion(region));

    }

    @Operation(summary = "Listar centro de acopio activos/disponibles")
    @GetMapping("/activos")
    public ResponseEntity<List<AcopioResponseDTO>>listarActivos(){
        return ResponseEntity.ok(centroAcopioService.listarActivo());
    }





}

