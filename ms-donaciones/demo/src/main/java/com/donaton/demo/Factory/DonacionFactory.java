package com.donaton.demo.Factory;

import com.donaton.demo.DTO.DonacionRequestDTO;
import com.donaton.demo.Model.Donacion;

public interface DonacionFactory {

    Donacion crear(DonacionRequestDTO dto);

}
