package org.example.chattrilogy.repository;

import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.domain.UserDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeviceTokenRepository extends JpaRepository<UserDeviceToken, Integer> {
    List<UserDeviceToken> findByUser(User user);
    UserDeviceToken findByToken(String token);
}
