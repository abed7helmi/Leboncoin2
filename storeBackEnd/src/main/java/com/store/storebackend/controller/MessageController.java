package com.store.storebackend.controller;

import com.store.storebackend.dto.ConversationDTO;
import com.store.storebackend.dto.MessageRequest;
import com.store.storebackend.entity.Message;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> envoyerMessage(@AuthenticationPrincipal Utilisateur utilisateur,
                                                   @RequestBody MessageRequest request) {
        if (utilisateur == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            return ResponseEntity.ok(messageService.envoyerMessage(request, utilisateur));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDTO>> getConversations(@AuthenticationPrincipal Utilisateur utilisateur) {
        if (utilisateur == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(messageService.getConversations(utilisateur));
    }

    @GetMapping("/conversation/{annonceId}")
    public ResponseEntity<List<Message>> getConversation(@AuthenticationPrincipal Utilisateur utilisateur,
                                                          @PathVariable Long annonceId) {
        if (utilisateur == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(messageService.getMessages(annonceId, utilisateur));
    }
}
