package com.lodoviko.convencao.api.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/convencao")
public class ConvencaoController {

    @GetMapping
    public String teste() {
        return "Projeto iniciado";
    }
}