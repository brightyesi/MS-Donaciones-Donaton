package com.donaton.demo.Service;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.DTO.DonacionResponseDTO;
import com.donaton.demo.Exception.DonacionNotFoundException;
import com.donaton.demo.Factory.DonacionFactory;
import com.donaton.demo.Factory.DonacionFactoryProvider;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.CentroAcopio;
import com.donaton.demo.Model.Donacion;
import com.donaton.demo.Model.EstadoDonacion;
import com.donaton.demo.Repository.CentroAcopioRepository;
import com.donaton.demo.Repository.DonacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonacionServiceImpl implements DonacionService{
    private final DonacionRepository donacionRepository;
    private final CentroAcopioRepository centroRepository;

    public DonacionServiceImpl(DonacionRepository donacionRepository, CentroAcopioRepository centroRepository) {
        this.donacionRepository = donacionRepository;
        this.centroRepository = centroRepository;
    }

    @Override
    public DonacionResponseDTO crearDonacion(DonacionRequestDTO request){
        CentroAcopio centro = centroRepository.findById(request.getCentroAcopioId())
                .orElseThrow(()-> new DonacionNotFoundException(
                        "Centro de acopio no encontrado con id " + request.getCentroAcopioId()

        ));
        DonacionFactory factory = DonacionFactoryProvider.getFactory(request.getCategoria());
        Donacion donacion =factory.crear(request);

        donacion.setCentroAcopio(centro);
        return toResponse(donacionRepository.save(donacion));
    }

    @Override
    public List<DonacionResponseDTO>listarTodas(){
        return donacionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DonacionResponseDTO obtenerPorId(Long id){
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(()->new DonacionNotFoundException("Donación no encontrada con id: " + id));
        return toResponse(donacion);
    }
    @Override
    public List<DonacionResponseDTO>listarPorCategoria(CategoriaDonacion categoria){
        return donacionRepository.findByCategoria(categoria)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<DonacionResponseDTO>listarPorEstado(EstadoDonacion estado){
        return donacionRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }

    @Override
    public List<DonacionResponseDTO> listarPorDonador(Long donadorId) {
        return donacionRepository.findByDonadorId(donadorId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private DonacionResponseDTO toResponse(Donacion d) {
        DonacionResponseDTO dto = new DonacionResponseDTO();
        dto.setId(d.getIdDonacion());
        dto.setRecurso(d.getRecurso());
        dto.setCategoria(d.getCategoria() != null ? d.getCategoria().name() : null);
        dto.setCantidad(d.getCantidad());
        dto.setUnidad(d.getUnidad());
        dto.setOrigen(d.getOrigen());
        dto.setFecha(d.getFecha() != null ? d.getFecha().toString() : null);
        dto.setNombreCentroAcopio(d.getCentroAcopio() != null ? d.getCentroAcopio().getNombre() : null);
        dto.setEstado(d.getEstado() != null ? d.getEstado().name() : null);
        return dto;
    }


}
