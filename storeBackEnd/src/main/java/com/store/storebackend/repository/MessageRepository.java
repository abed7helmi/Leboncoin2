package com.store.storebackend.repository;

import com.store.storebackend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.annonce.id = :annonceId " +
           "AND (m.expediteur.id = :userId OR m.destinataire.id = :userId) " +
           "ORDER BY m.dateEnvoi ASC")
    List<Message> findConversation(@Param("annonceId") Long annonceId, @Param("userId") Long userId);

    @Query("SELECT DISTINCT m.annonce.id FROM Message m " +
           "WHERE m.expediteur.id = :userId OR m.destinataire.id = :userId")
    List<Long> findConversationAnnonceIds(@Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.annonce.id = :annonceId " +
           "AND m.destinataire.id = :userId AND m.lu = false")
    long countNonLus(@Param("annonceId") Long annonceId, @Param("userId") Long userId);
}
