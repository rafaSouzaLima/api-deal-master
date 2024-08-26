package com.dealmaster.api.dtos;

import java.time.LocalDate;

public record ContratoResponseDto(
    UsuarioResponseDto usuario,
    ParceiroDto parceiro,
    String texto,
    LocalDate dataVencimento
) {}
