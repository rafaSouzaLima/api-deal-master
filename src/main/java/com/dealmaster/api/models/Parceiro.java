package com.dealmaster.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Parceiro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String cpfCnpj;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoParceiro tipo;

    @Column(nullable = false)
    private String nome;

    public Parceiro(String cpfCnpj, TipoParceiro tipo, String nome) {
        this.cpfCnpj = cpfCnpj;
        this.tipo = tipo;
        this.nome = nome;
    }
}