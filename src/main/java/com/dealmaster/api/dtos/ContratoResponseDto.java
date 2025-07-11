package com.dealmaster.api.dtos;

import java.time.LocalDate;

public record ContratoResponseDto(
    String codigo,
    UsuarioResponseDto usuario,
    ParceiroDto parceiro,
    String texto,
    LocalDate dataVencimento
) {}
