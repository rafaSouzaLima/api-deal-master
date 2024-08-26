package com.dealmaster.api.models;

public enum TipoUsuario {
    FUNCIONARIO("FUNCIONARIO"),
    GERENTE("GERENTE");

    private String tipo;

    TipoUsuario(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() { return tipo; }
}
