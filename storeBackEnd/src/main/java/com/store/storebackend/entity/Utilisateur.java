package com.store.storebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.store.storebackend.enums.RoleUtilisateur;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String motDePasse;

    private String telephone;

    private String ville;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUtilisateur role = RoleUtilisateur.UTILISATEUR;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    private LocalDateTime dateModification;

    @JsonIgnoreProperties("vendeur")
    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Annonce> annonces = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "expediteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messagesEnvoyes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "destinataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messagesRecus = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}
