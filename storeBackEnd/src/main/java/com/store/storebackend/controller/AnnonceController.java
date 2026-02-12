package com.store.storebackend.controller;

import com.store.storebackend.dto.AnnonceRequest;
import com.store.storebackend.entity.Annonce;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.service.AnnonceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annonces")
@AllArgsConstructor
public class AnnonceController {

    private final AnnonceService annonceService;

    @GetMapping
    public List<Annonce> getAllAnnonces(@RequestParam(name = "q", required = false) String query,
                                       @RequestParam(required = false) Long categorieId) {
        if (query != null || categorieId != null) {
            return annonceService.rechercher(query, categorieId);
        }
        return annonceService.getAllAnnonces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        return annonceService.getAnnonceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mes-annonces")
    public ResponseEntity<List<Annonce>> getMesAnnonces(@AuthenticationPrincipal Utilisateur utilisateur) {
        if (utilisateur == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(annonceService.getAnnoncesByVendeur(utilisateur.getId()));
    }

    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(@AuthenticationPrincipal Utilisateur utilisateur,
                                                  @RequestBody AnnonceRequest request) {
        if (utilisateur == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            return ResponseEntity.ok(annonceService.createAnnonce(request, utilisateur));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
