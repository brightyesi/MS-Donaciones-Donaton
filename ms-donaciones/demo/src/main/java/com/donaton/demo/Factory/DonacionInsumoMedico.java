package com.donaton.demo.Factory;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.Model.CategoriaDonacion;
import com.donaton.demo.Model.Donacion;

public class DonacionInsumoMedico implements DonacionFactory{

    @Override
    public Donacion crear(DonacionRequestDTO dto){
        if (dto.getUnidad() == null || (!dto.getUnidad().equals("cajas") && !dto.getUnidad().equals("unidades"))){
            throw new IllegalArgumentException(
                    "Unidad invalida para insumo medico. USe 'cajas' o 'unidades' ");
        }
        Donacion d = new Donacion();
        d.setRecurso(dto.getRecurso());
        d.setCategoria(CategoriaDonacion.INSUMO_MEDICO);
        d.setCantidad(dto.getCantidad());
        d.setUnidad(dto.getUnidad());
        d.setOrigen(dto.getOrigen());
        d.setDonadorId(dto.getDonadorId());
        return d;
    }

}
