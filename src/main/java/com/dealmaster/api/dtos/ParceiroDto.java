package com.dealmaster.api.dtos;


import com.dealmaster.api.models.TipoParceiro;

public record ParceiroDto(
    String nome,
    TipoParceiro tipo,
    String cpfCnpj
) {}