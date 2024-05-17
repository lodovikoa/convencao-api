package com.lodoviko.convencao.api.v1.controller;

import com.lodoviko.convencao.api.v1.dto.input.AuthenticationDTO;
import com.lodoviko.convencao.api.v1.dto.model.LoginResponseDTO;
import com.lodoviko.convencao.core.security.TokenService;
import com.lodoviko.convencao.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {

        System.out.println("Login:    " + data.login());
        System.out.println("Password: " + data.password());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        System.out.println("Token: " + token);
        return ResponseEntity.ok(new LoginResponseDTO(token));
        //return token;
    }
}
