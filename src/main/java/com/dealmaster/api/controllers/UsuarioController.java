package com.dealmaster.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dealmaster.api.dtos.UsuarioLoginDto;
import com.dealmaster.api.dtos.UsuarioRegisterDto;
import com.dealmaster.api.dtos.UsuarioResponseDto;
import com.dealmaster.api.services.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

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
}
