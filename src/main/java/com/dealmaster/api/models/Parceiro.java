package com.dealmaster.api.models;

import jakarta.persistence.Entity;
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

    private String cpfCnpj;
    private String tipo;
    private String nome;

    public Parceiro(String cpfCnpj, String tipo, String nome) {
        this.cpfCnpj = cpfCnpj;
        this.tipo = tipo;
        this.nome = nome;
    }
}