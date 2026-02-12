package com.store.storebackend.service;

import com.store.storebackend.dto.LoginRequest;
import com.store.storebackend.dto.LoginResponse;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.repository.UtilisateurRepository;
import com.store.storebackend.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;

    public Optional<LoginResponse> login(LoginRequest request) {
        return utilisateurRepository.findByEmail(request.getEmail())
                .filter(u -> u.getMotDePasse().equals(request.getMotDePasse()))
                .map(u -> {
                    String token = jwtService.generateToken(u.getId(), u.getEmail(), u.getRole().name());
                    return LoginResponse.from(token, u);
                });
    }

    public Utilisateur getProfil(Utilisateur utilisateur) {
        return utilisateur;
    }
}
