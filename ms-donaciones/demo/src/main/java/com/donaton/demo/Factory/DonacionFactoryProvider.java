package com.donaton.demo.Factory;

import com.donaton.demo.Model.CategoriaDonacion;

public class DonacionFactoryProvider {

    public static DonacionFactory getFactory(CategoriaDonacion categoria){
        return switch (categoria){
            case ALIMENTO_NO_PERECIBLE -> new DonacionAlimento();
            case ROPA -> new DonacionRopa();
            case INSUMO_MEDICO -> new DonacionInsumoMedico();
        };
    }

}
/**
 * PATRÓN: Factory Method — Proveedor de Fábricas
 * Método estático que retorna la fábrica correcta según la categoría.
 * El Service lo llama directamente sin necesidad de inyección.
 */
