package com.dealmaster.api.dtos;

import java.time.LocalDate;

public record ContratoUpdateRequestDto(
    String codigo,
    ParceiroDto parceiro,
    String texto,
    LocalDate dataVencimento
) {}
