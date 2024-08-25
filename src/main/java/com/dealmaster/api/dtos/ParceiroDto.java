package com.dealmaster.api.dtos;

public record ParceiroDto(
    String nome,
    String tipo,
    String cpfCnpj
) {}