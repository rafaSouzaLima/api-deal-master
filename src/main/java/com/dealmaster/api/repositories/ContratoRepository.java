package com.dealmaster.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealmaster.api.models.Contrato;
import com.dealmaster.api.models.Usuario;

public interface ContratoRepository extends JpaRepository<Contrato, Integer>{
    List<Contrato> findByUsuario(Usuario usuario);
}
