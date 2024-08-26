package com.dealmaster.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.dealmaster.api.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    UserDetails findByEmail(String email);
    Usuario findUsuarioByEmail(String email);
}
