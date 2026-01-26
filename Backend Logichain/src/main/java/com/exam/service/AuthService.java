package com.exam.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.entities.User;
import com.exam.repository.UserRepository;
import com.exam.security.JwtUtils;
import com.exam.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    
    public Map<String, Object> register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("email", saved.getEmail());
        response.put("role", saved.getRole());
        return response;
    }
    
    public Map<String, Object> login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        String token = jwtUtils.generateToken(principal);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", principal.getEmail());
        response.put("role", principal.getUserRole());
        return response;
    }
}
