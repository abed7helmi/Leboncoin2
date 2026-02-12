package com.store.storebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private Long annonceId;
    private String contenu;
}
