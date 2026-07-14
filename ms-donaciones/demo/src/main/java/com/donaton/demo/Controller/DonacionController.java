package com.donaton.demo.Controller;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.DTO.DonacionResponseDTO;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.EstadoDonacion;
import com.donaton.demo.Service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@Tag(name = "Donaciones", description = "Gestion de donaciones")
public class DonacionController {
    
    private final DonacionService donacionService;
    
    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    @Operation(summary = "Registrar una nueva donacion")
    @PostMapping
    public ResponseEntity<DonacionResponseDTO> crearDonacion(@Valid @RequestBody DonacionRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donacionService.crearDonacion(requestDTO));
    }

    @Operation(summary = "Listar absolutamente todas las donaciones registradas (Uso Dashboard)")
    @GetMapping
    public ResponseEntity<List<DonacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(donacionService.listarTodas());
    }

    @Operation(summary = "Obtener por id la donacion")
    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar el estado de una donacion especifica")
    @PutMapping("/{id}/estado")
    public ResponseEntity<DonacionResponseDTO> actualizarEstado(
            @PathVariable Long id, 
            @Valid @RequestBody EstadoRequest payload) {
        return ResponseEntity.ok(donacionService.actualizarEstadoDonacion(id, payload.getEstado()));
    }

    @Operation(summary = "Listar donaciones filtradas por categoria")
    @GetMapping("/categoria/{cat}")
    public ResponseEntity<List<DonacionResponseDTO>> listarPorCategoria(@PathVariable CategoriaDonacion cat) {
        return ResponseEntity.ok(donacionService.listarPorCategoria(cat));
    }

    @Operation(summary = "Listar donaciones filtradas por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DonacionResponseDTO>> listarPorEstado(@PathVariable EstadoDonacion estado) {
        return ResponseEntity.ok(donacionService.listarPorEstado(estado));
    }

    @Operation(summary = "Listar donaciones por usuario donador")
    @GetMapping("/usuario/{donadorId}")
    public ResponseEntity<List<DonacionResponseDTO>> listarPorUsuario(@PathVariable Long donadorId) {
        return ResponseEntity.ok(donacionService.listarPorDonador(donadorId));
    }

    public static class EstadoRequest {
        private String estado;

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}
