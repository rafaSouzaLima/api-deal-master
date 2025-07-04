package com.dealmaster.api.dtos;

import java.time.LocalDate;

public record ContratoRequestDto(
    ParceiroDto parceiro,
    String texto,
    LocalDate dataVencimento
) {}