package com.donaton.demo.Factory;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.Donacion;

public class DonacionAlimento implements DonacionFactory{

    @Override
    public Donacion crear (DonacionRequestDTO dto){
        if (dto.getUnidad()==null || (!dto.getUnidad().equals("kg") && !dto.getUnidad().equals("cajas"))){
            throw new IllegalArgumentException(
                    "Unidad invalida para alimeto. Debe usar 'kg' o 'cajas' ");
        }
        Donacion d = new Donacion();
        d.setRecurso(dto.getRecurso());
        d.setCategoria(CategoriaDonacion.ALIMENTO_NO_PERECIBLE);
        d.setCantidad(dto.getCantidad());
        d.setUnidad(dto.getUnidad());
        d.setOrigen(dto.getOrigen());
        d.setDonadorId(dto.getDonadorId());
        return d;
    }
}
