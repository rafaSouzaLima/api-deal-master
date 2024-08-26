package com.dealmaster.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealmaster.api.dtos.EmpresaDto;
import com.dealmaster.api.dtos.UsuarioLoginDto;
import com.dealmaster.api.dtos.UsuarioRegisterDto;
import com.dealmaster.api.dtos.UsuarioResponseDto;
import com.dealmaster.api.models.Empresa;
import com.dealmaster.api.models.Usuario;
import com.dealmaster.api.repositories.EmpresaRepository;
import com.dealmaster.api.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public UsuarioResponseDto registrarUsuario(UsuarioRegisterDto usuarioRegisterDto) {
        Empresa empresa = empresaRepository.findByCnpj(usuarioRegisterDto.empresa().cnpj());
        if(empresa == null) {
            empresa = new Empresa(
                usuarioRegisterDto.empresa().cnpj(), 
                usuarioRegisterDto.empresa().nome()
            );
            
            empresaRepository.save(empresa);
        }

        Usuario usuario = new Usuario(
            usuarioRegisterDto.nome(),
            usuarioRegisterDto.email(),
            usuarioRegisterDto.senha(),
            usuarioRegisterDto.tipo(),
            empresa
        );

        usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
            usuario.getNome(), 
            usuario.getEmail(), 
            usuario.getTipo(),
            new EmpresaDto(
                usuario.getEmpresa().getCnpj(), usuario.getEmpresa().getCnpj()
            )
        );
    }
}
