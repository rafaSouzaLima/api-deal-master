package com.dealmaster.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealmaster.api.dtos.ContratoRequestDto;
import com.dealmaster.api.models.Contrato;
import com.dealmaster.api.models.Parceiro;
import com.dealmaster.api.models.Usuario;
import com.dealmaster.api.repositories.ContratoRepository;
import com.dealmaster.api.repositories.UsuarioRepository;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Contrato> getContratosByEmail(String email) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
        return contratoRepository.findByUsuario(usuario);
    }

    public Contrato addContrato(ContratoRequestDto contratoRequestDto) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(contratoRequestDto.usuario().email());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        Contrato contrato = new Contrato(
            usuario,
            new Parceiro(contratoRequestDto.parceiro().nome(), contratoRequestDto.parceiro().tipo(), contratoRequestDto.parceiro().cpfCnpj()),
            contratoRequestDto.contrato(),
            contratoRequestDto.dataVencimento()
        );

        return contratoRepository.save(contrato);
    }
}