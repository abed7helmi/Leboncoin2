package com.store.storebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.store.storebackend.enums.EtatAnnonce;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annonce")
@Getter
@Setter
@NoArgsConstructor
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;

    private String ville;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatAnnonce etat = EtatAnnonce.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    private LocalDateTime dateModification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendeur_id", nullable = false)
    @JsonIgnoreProperties({"annonces", "messagesEnvoyes", "messagesRecus"})
    private Utilisateur vendeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    @JsonIgnoreProperties({"annonces", "sousCategories", "parent"})
    private Categorie categorie;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("annonce")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("annonce")
    private List<Message> messages = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}
