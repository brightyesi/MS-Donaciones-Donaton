package com.donaton.bff_gateway.dto;

public class ErrorResponseDTO {

    private boolean error;
    private String mensaje;
    private int codigo;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(boolean error, String mensaje, int codigo) {
        this.error = error;
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}