package com.store.storebackend.dto;

import com.store.storebackend.entity.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String role;

    public static LoginResponse from(String token, Utilisateur u) {
        return new LoginResponse(token, u.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getRole().name());
    }
}
