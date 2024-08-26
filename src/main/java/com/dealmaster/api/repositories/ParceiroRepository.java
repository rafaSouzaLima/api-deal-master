package com.dealmaster.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealmaster.api.models.Parceiro;


public interface ParceiroRepository extends JpaRepository<Parceiro, Integer>{
    Parceiro findByCpfCnpj(String cpfCnpj);
}
