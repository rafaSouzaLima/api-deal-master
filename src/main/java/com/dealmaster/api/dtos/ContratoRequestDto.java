package com.dealmaster.api.dtos;

import java.time.LocalDate;

public record ContratoRequestDto(
    ParceiroDto parceiro,
    UsuarioEmailDto usuario,
    String contrato,
    LocalDate dataVencimento
) {}