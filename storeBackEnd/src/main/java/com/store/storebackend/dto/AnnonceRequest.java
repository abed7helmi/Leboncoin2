package com.store.storebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AnnonceRequest {
    private String titre;
    private String description;
    private BigDecimal prix;
    private String ville;
    private Long categorieId;
    private String imageUrl;
}
