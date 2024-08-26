package com.dealmaster.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dealmaster.api.config.TokenService;
import com.dealmaster.api.dtos.UsuarioLoginDto;
import com.dealmaster.api.dtos.UsuarioLoginResponseDto;
import com.dealmaster.api.dtos.UsuarioRegisterDto;
import com.dealmaster.api.dtos.UsuarioResponseDto;
import com.dealmaster.api.models.Usuario;
import com.dealmaster.api.services.UsuarioService;

@RestController
@RequestMapping("api/v1")
public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioService usuarioService;
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

    
    
}
