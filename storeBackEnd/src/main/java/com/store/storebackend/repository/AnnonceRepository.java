package com.store.storebackend.repository;

import com.store.storebackend.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByVendeurIdOrderByDateCreationDesc(Long vendeurId);

    @Query("SELECT a FROM Annonce a WHERE " +
           "(:query IS NULL OR LOWER(a.titre) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:categorieId IS NULL OR a.categorie.id = :categorieId)")
    List<Annonce> rechercher(@Param("query") String query, @Param("categorieId") Long categorieId);
}
