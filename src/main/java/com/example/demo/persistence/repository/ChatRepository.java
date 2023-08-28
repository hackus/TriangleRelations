package com.example.demo.persistence.repository;

import com.example.demo.persistence.entities.Chat;
import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id IN :usersIDs GROUP BY c HAVING COUNT(DISTINCT u) = :numberOfUsers")
    Optional<Chat> getChat(@Param("usersIDs") List<Long> usersIDs, int numberOfUsers);


    @Query("SELECT c FROM Chat c JOIN c.users u GROUP BY c HAVING COUNT(DISTINCT u) = :numberOfUsers")
    List<Chat> getUsersForChat(int numberOfUsers);


    @Query("SELECT c FROM Chat c WHERE c.id = :id")
    Optional<Chat> getChatById(Long id);
}