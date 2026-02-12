package com.store.storebackend.controller;

import com.store.storebackend.dto.LoginRequest;
import com.store.storebackend.dto.LoginResponse;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/profil")
    public ResponseEntity<Utilisateur> getProfil(@AuthenticationPrincipal Utilisateur utilisateur) {
        if (utilisateur == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(authService.getProfil(utilisateur));
    }
}
