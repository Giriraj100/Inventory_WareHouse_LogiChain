package com.exam.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entities.User;
import com.exam.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
private final AuthService authService;
    
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        return authService.register(user);
    }
    
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        return authService.login(
            credentials.get("email"), 
            credentials.get("password")
        );
    }
}
