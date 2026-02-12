package com.store.storebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ConversationDTO {
    private Long annonceId;
    private String annonceTitre;
    private String annonceImage;
    private Long interlocuteurId;
    private String interlocuteurNom;
    private String dernierMessage;
    private LocalDateTime dateDernierMessage;
    private long nonLus;
}
