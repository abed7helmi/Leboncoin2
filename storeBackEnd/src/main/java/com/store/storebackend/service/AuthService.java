package com.store.storebackend.service;

import com.store.storebackend.dto.LoginRequest;
import com.store.storebackend.dto.LoginResponse;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
        );

        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
        String token = jwtService.generateToken(utilisateur.getId(), utilisateur.getEmail(), utilisateur.getRole().name());
        return LoginResponse.from(token, utilisateur);
    }

    public Utilisateur getProfil(Utilisateur utilisateur) {
        return utilisateur;
    }
}
