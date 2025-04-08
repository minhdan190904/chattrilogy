package org.example.chattrilogy.repository;

import org.example.chattrilogy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);

    List<User> findByIsOnline(boolean isOnline);
}
