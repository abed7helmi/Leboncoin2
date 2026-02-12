package com.store.storebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorie")
@Getter
@Setter
@NoArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Categorie parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Categorie> sousCategories = new ArrayList<>();

    @OneToMany(mappedBy = "categorie")
    private List<Annonce> annonces = new ArrayList<>();
}
