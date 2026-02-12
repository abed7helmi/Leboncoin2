package com.store.storebackend.service;

import com.store.storebackend.dto.AnnonceRequest;
import com.store.storebackend.entity.Annonce;
import com.store.storebackend.entity.Categorie;
import com.store.storebackend.entity.Image;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.repository.AnnonceRepository;
import com.store.storebackend.repository.CategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final CategorieRepository categorieRepository;

    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    public List<Annonce> getAnnoncesByVendeur(Long vendeurId) {
        return annonceRepository.findByVendeurIdOrderByDateCreationDesc(vendeurId);
    }

    public Annonce createAnnonce(AnnonceRequest request, Utilisateur vendeur) {
        Categorie categorie = categorieRepository.findById(request.getCategorieId())
                .orElseThrow(() -> new IllegalArgumentException("Categorie introuvable"));

        Annonce annonce = new Annonce();
        annonce.setTitre(request.getTitre());
        annonce.setDescription(request.getDescription());
        annonce.setPrix(request.getPrix());
        annonce.setVille(request.getVille());
        annonce.setVendeur(vendeur);
        annonce.setCategorie(categorie);

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            Image image = new Image();
            image.setUrl(request.getImageUrl());
            image.setDescription(request.getTitre());
            image.setOrdre(1);
            image.setAnnonce(annonce);
            annonce.getImages().add(image);
        }

        return annonceRepository.save(annonce);
    }

    public List<Annonce> rechercher(String query, Long categorieId) {
        return annonceRepository.rechercher(query, categorieId);
    }
}
