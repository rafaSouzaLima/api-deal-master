package com.dealmaster.api.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dealmaster.api.models.Contrato;
import com.dealmaster.api.models.Usuario;

public interface ContratoRepository extends JpaRepository<Contrato, Integer>{
    List<Contrato> findByUsuario(Usuario usuario);
    
    Contrato findByCodigo(String codigo);

    @Query("SELECT c.codigo FROM Contrato c ORDER BY c.id DESC")
    List<String> findUltimoCodigo(Pageable pageable);
}
