package com.donaton.demo.Factory;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.Donacion;

public class DonacionRopa implements DonacionFactory{

    @Override
    public Donacion crear(DonacionRequestDTO dto){
        if (dto.getUnidad()==null || !dto.getUnidad().equals("unidades")){
            throw new IllegalArgumentException(
                    "Unidad invalida para ropa. Use 'unidades' ");
        }
        Donacion d = new Donacion();
        d.setRecurso(dto.getRecurso());
        d.setCategoria(CategoriaDonacion.ROPA);
        d.setCantidad(dto.getCantidad());
        d.setUnidad(dto.getUnidad());
        d.setOrigen(dto.getOrigen());
        d.setDonadorId(dto.getDonadorId());
        return d;
    }


}
