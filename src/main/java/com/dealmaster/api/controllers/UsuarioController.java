package com.dealmaster.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dealmaster.api.config.TokenService;
import com.dealmaster.api.dtos.*;
import com.dealmaster.api.models.Usuario;
import com.dealmaster.api.services.ContratoService;
import com.dealmaster.api.services.UsuarioService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ContratoService contratoService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/users/register")
    public ResponseEntity<UsuarioResponseDto> register(@RequestBody @Validated UsuarioRegisterDto usuarioRegisterDto) {
        try {
            UsuarioRegisterDto novo = new UsuarioRegisterDto(
                usuarioRegisterDto.nome(),
                usuarioRegisterDto.email(),
                new BCryptPasswordEncoder().encode(usuarioRegisterDto.senha()),
                usuarioRegisterDto.tipo(),
                usuarioRegisterDto.empresa()
            );

            UsuarioResponseDto usuarioResponse = usuarioService.registrarUsuario(novo);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<UsuarioLoginResponseDto> login(@RequestBody @Validated UsuarioLoginDto usuarioLoginDto) {
        try {
            var emailESenha = new UsernamePasswordAuthenticationToken(usuarioLoginDto.email(), usuarioLoginDto.senha());
            var auth = authenticationManager.authenticate(emailESenha);

            var token = tokenService.generateToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new UsuarioLoginResponseDto(token));
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }

    @GetMapping("/users/company")
    public ResponseEntity<EmpresaDto> getCompanyByUserEmail(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String email = tokenService.validateToken(token);
            EmpresaDto empresa = usuarioService.getEmpresaByEmail(email);
          
            return ResponseEntity.ok(empresa);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/users/contract")
    public ResponseEntity<List<ContratoResponseDto>> getContractsByUserEmail(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String email = tokenService.validateToken(token);

            List<ContratoResponseDto> contratos = contratoService.getContratosByEmail(email);
            return ResponseEntity.ok(contratos);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/users/contract/add")
    public ResponseEntity<Void> addContract(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ContratoRequestDto contratoRequestDto) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String email = tokenService.validateToken(token);

            contratoService.addContrato(email, contratoRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
