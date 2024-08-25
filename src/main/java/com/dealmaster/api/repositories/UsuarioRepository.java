package com.dealmaster.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealmaster.api.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Usuario findByEmail(String email);
}
