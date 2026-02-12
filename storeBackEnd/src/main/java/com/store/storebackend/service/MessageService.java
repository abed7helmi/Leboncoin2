package com.store.storebackend.service;

import com.store.storebackend.dto.ConversationDTO;
import com.store.storebackend.dto.MessageRequest;
import com.store.storebackend.entity.Annonce;
import com.store.storebackend.entity.Message;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.repository.AnnonceRepository;
import com.store.storebackend.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final AnnonceRepository annonceRepository;

    public Message envoyerMessage(MessageRequest request, Utilisateur expediteur) {
        Annonce annonce = annonceRepository.findById(request.getAnnonceId())
                .orElseThrow(() -> new IllegalArgumentException("Annonce introuvable"));

        Utilisateur destinataire = annonce.getVendeur();
        if (destinataire.getId().equals(expediteur.getId())) {
            throw new IllegalArgumentException("Vous ne pouvez pas vous envoyer un message");
        }

        Message message = new Message();
        message.setContenu(request.getContenu());
        message.setExpediteur(expediteur);
        message.setDestinataire(destinataire);
        message.setAnnonce(annonce);

        return messageRepository.save(message);
    }

    public List<ConversationDTO> getConversations(Utilisateur utilisateur) {
        List<Long> annonceIds = messageRepository.findConversationAnnonceIds(utilisateur.getId());
        List<ConversationDTO> conversations = new ArrayList<>();

        for (Long annonceId : annonceIds) {
            List<Message> messages = messageRepository.findConversation(annonceId, utilisateur.getId());
            if (messages.isEmpty()) continue;

            Message dernier = messages.get(messages.size() - 1);
            Annonce annonce = dernier.getAnnonce();

            Utilisateur interlocuteur = annonce.getVendeur().getId().equals(utilisateur.getId())
                    ? dernier.getExpediteur().getId().equals(utilisateur.getId())
                        ? dernier.getDestinataire()
                        : dernier.getExpediteur()
                    : annonce.getVendeur();

            String image = annonce.getImages().isEmpty() ? null : annonce.getImages().get(0).getUrl();
            long nonLus = messageRepository.countNonLus(annonceId, utilisateur.getId());

            conversations.add(new ConversationDTO(
                    annonceId,
                    annonce.getTitre(),
                    image,
                    interlocuteur.getId(),
                    interlocuteur.getPrenom() + " " + interlocuteur.getNom(),
                    dernier.getContenu(),
                    dernier.getDateEnvoi(),
                    nonLus
            ));
        }

        conversations.sort((a, b) -> b.getDateDernierMessage().compareTo(a.getDateDernierMessage()));
        return conversations;
    }

    @Transactional
    public List<Message> getMessages(Long annonceId, Utilisateur utilisateur) {
        List<Message> messages = messageRepository.findConversation(annonceId, utilisateur.getId());

        for (Message m : messages) {
            if (m.getDestinataire().getId().equals(utilisateur.getId()) && !m.isLu()) {
                m.setLu(true);
                messageRepository.save(m);
            }
        }

        return messages;
    }
}
