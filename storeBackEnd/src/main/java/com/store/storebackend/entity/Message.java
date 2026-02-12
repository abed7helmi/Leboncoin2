package com.store.storebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @Column(nullable = false)
    private boolean lu = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateEnvoi = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_id", nullable = false)
    @JsonIgnoreProperties({"messagesEnvoyes", "messagesRecus", "annonces", "motDePasse"})
    private Utilisateur expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id", nullable = false)
    @JsonIgnoreProperties({"messagesEnvoyes", "messagesRecus", "annonces", "motDePasse"})
    private Utilisateur destinataire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_id", nullable = false)
    @JsonIgnoreProperties({"messages", "vendeur"})
    private Annonce annonce;
}
