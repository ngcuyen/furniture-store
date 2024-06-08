package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.sevices.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {
    @Autowired
    private TokenService tokenService;
}
