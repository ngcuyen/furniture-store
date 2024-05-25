package com.hutech.furniturestore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HomeController {
    @GetMapping("/")
    public String hello() {
//        model.addAttribute("message", "XIN CHÀO TRƯỜNG ĐẠI HỌC CÔNG NGHỆ THÀNH PHỐ HỒ CHÍ MINH!");

        return "Hello World";
    }
}

