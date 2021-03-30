package com.global.company.configserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConfigServerController {

    @GetMapping(path = "/")
    public Mono greetings() {
        return Mono.just("Greetings from config-server !!!");
    }
}
