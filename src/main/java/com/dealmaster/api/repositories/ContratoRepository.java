package com.dealmaster.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealmaster.api.models.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Integer>{
    //Contrato findByDescricao(String descricao);
}
