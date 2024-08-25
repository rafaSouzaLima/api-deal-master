package com.dealmaster.api.dtos;

import com.dealmaster.api.models.TipoUsuario;

public record UsuarioRegisterDto(
    String nome,
    String email,
    String senha,
    TipoUsuario tipo,
    EmpresaDto empresa
) {}
