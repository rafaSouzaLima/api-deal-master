package com.dealmaster.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealmaster.api.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
    Empresa findByCnpj(String cnpj);
}
