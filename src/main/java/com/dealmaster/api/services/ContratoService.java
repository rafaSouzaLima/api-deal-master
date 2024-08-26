package com.dealmaster.api.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dealmaster.api.dtos.ContratoRequestDto;
import com.dealmaster.api.dtos.ContratoResponseDto;
import com.dealmaster.api.dtos.ContratoUpdateRequestDto;
import com.dealmaster.api.dtos.EmpresaDto;
import com.dealmaster.api.dtos.ParceiroDto;
import com.dealmaster.api.dtos.UsuarioResponseDto;
import com.dealmaster.api.models.Contrato;
import com.dealmaster.api.models.Parceiro;
import com.dealmaster.api.models.Usuario;
import com.dealmaster.api.repositories.ContratoRepository;
import com.dealmaster.api.repositories.ParceiroRepository;
import com.dealmaster.api.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ParceiroRepository parceiroRepository;

    @Transactional
    public List<ContratoResponseDto> getContratosByEmail(String email) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        List<Contrato> contratos = contratoRepository.findByUsuario(usuario);

        List<ContratoResponseDto> contratoResponseDtos = new ArrayList<>();
        for(var contrato : contratos) {
            contratoResponseDtos.add(
                new ContratoResponseDto(
                    contrato.getCodigo(),
                    new UsuarioResponseDto(
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTipo(),
                        new EmpresaDto(
                            usuario.getEmpresa().getCnpj(),
                            usuario.getEmpresa().getCnpj()
                        )
                    ),
                    new ParceiroDto(
                        contrato.getParceiro().getNome(),
                        contrato.getParceiro().getTipo(), 
                        contrato.getParceiro().getCpfCnpj()
                    ), 
                    contrato.getTexto(),
                    contrato.getDataVencimento()
                )
            );
        }

        return contratoResponseDtos;
    }

    @Transactional
    public Contrato addContrato(String email, ContratoRequestDto contratoRequestDto) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        Parceiro parceiro = parceiroRepository.findByCpfCnpj(contratoRequestDto.parceiro().cpfCnpj());
        if(parceiro == null) {
            parceiro = new Parceiro(
                contratoRequestDto.parceiro().cpfCnpj(),
                contratoRequestDto.parceiro().tipo(),
                contratoRequestDto.parceiro().nome()
            );
            parceiroRepository.save(parceiro);
        }

        //Com uma página de tamanho 1 é impedido de ele pegar todos os códigos de uma vez.
        Pageable pageable = PageRequest.of(0, 1, Sort.by("id").descending());
        List<String> codigos = contratoRepository.findUltimoCodigo(pageable);
        String codigo = codigos.isEmpty() ? null : codigos.get(0);

        if(codigo == null || codigo.isEmpty()) codigo = "C000001";
        else {
            int num = Integer.parseInt(codigo.substring(1));
            num++;
            codigo = "C" + String.format("%06d", num);
        }

        Contrato contrato = new Contrato(
            codigo,
            usuario,
            parceiro,
            contratoRequestDto.texto(),
            contratoRequestDto.dataVencimento()
        );

        return contratoRepository.save(contrato);
    }

    @Transactional
    public void updateContrato(String email, ContratoUpdateRequestDto contratoUpdateRequestDto) {
        Contrato contrato = contratoRepository.findByCodigo(contratoUpdateRequestDto.codigo());
        if(contrato == null || !contrato.getUsuario().getEmail().equals(email)) {
            throw new IllegalArgumentException("Contrato não existe!");
        }

        Parceiro parceiro = parceiroRepository.findByCpfCnpj(contratoUpdateRequestDto.parceiro().cpfCnpj());
        if(parceiro == null) {
            parceiro = new Parceiro(
                contratoUpdateRequestDto.parceiro().cpfCnpj(),
                contratoUpdateRequestDto.parceiro().tipo(),
                contratoUpdateRequestDto.parceiro().nome()
            );
        } else {
            parceiro.setNome(contratoUpdateRequestDto.parceiro().nome());
        }
        parceiroRepository.save(parceiro);

        contrato.setParceiro(parceiro);
        contrato.setTexto(contratoUpdateRequestDto.texto());
        contrato.setDataVencimento(contratoUpdateRequestDto.dataVencimento());
        
        contratoRepository.save(contrato);
    }
}