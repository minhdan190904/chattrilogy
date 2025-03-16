package org.example.chattrilogy.repository;

import org.example.chattrilogy.domain.Chat;
import org.example.chattrilogy.domain.Message;
import org.example.chattrilogy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Boolean existsByUser1AndUser2(User user1, User user2);
    List<Chat> findByUser1IdOrUser2Id(int user1Id, int user2Id);
}
