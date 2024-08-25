package com.dealmaster.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.dealmaster.api.dtos.*;
import com.dealmaster.api.models.Contrato;
import com.dealmaster.api.services.UsuarioService;
import com.dealmaster.api.services.ContratoService;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContratoService contratoService;

    @PostMapping("/users/register")
    public ResponseEntity<UsuarioResponseDto> register(@RequestBody UsuarioRegisterDto usuarioRegisterDto) {
        try {
            UsuarioResponseDto usuarioResponse = usuarioService.registrarUsuario(usuarioRegisterDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<UsuarioResponseDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        try {
            UsuarioResponseDto usuarioResponse = usuarioService.loginUsuario(usuarioLoginDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioResponse);
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }

    @PostMapping("/company")
    public ResponseEntity<EmpresaDto> getCompanyByUserEmail(@RequestBody UsuarioEmailDto usuarioEmailDto) {
        try {
            EmpresaDto empresa = usuarioService.getEmpresaByEmail(usuarioEmailDto.email());
            return ResponseEntity.ok(empresa);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/contract")
    public ResponseEntity<List<Contrato>> getContractsByUserEmail(@RequestBody UsuarioEmailDto usuarioEmailDto) {
        try {
            List<Contrato> contratos = contratoService.getContratosByEmail(usuarioEmailDto.email());
            return ResponseEntity.ok(contratos);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/contract/add")
    public ResponseEntity<Contrato> addContract(@RequestBody ContratoRequestDto contratoRequestDto) {
        try {
            Contrato contrato = contratoService.addContrato(contratoRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(contrato);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
