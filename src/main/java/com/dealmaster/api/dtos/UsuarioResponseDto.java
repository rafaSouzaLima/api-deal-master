package com.dealmaster.api.dtos;

import com.dealmaster.api.models.TipoUsuario;

public record UsuarioResponseDto(
    String nome,
    String email,
    TipoUsuario tipo,
    EmpresaDto empresa
) {}
