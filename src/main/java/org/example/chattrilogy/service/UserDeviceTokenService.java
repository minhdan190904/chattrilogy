package org.example.chattrilogy.service;

import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.domain.UserDeviceToken;
import org.example.chattrilogy.repository.UserDeviceTokenRepository;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserDeviceTokenService {
    private final UserDeviceTokenRepository userDeviceTokenRepository;
    private final UserService userService;

    public UserDeviceTokenService(UserDeviceTokenRepository userDeviceTokenRepository, UserService userService) {
        this.userDeviceTokenRepository = userDeviceTokenRepository;
        this.userService = userService;
    }

    public UserDeviceToken createUserDeviceToken(UserDeviceToken userDeviceToken) {
        UserDeviceToken newUserDeviceToken = userDeviceTokenRepository.findByToken(userDeviceToken.getToken());
        if (newUserDeviceToken == null) {
            return this.userDeviceTokenRepository.save(userDeviceToken);
        }
        else {
            newUserDeviceToken.setUser(userDeviceToken.getUser());
            newUserDeviceToken.setTimeStamp(LocalDateTime.now().toString());
            return this.userDeviceTokenRepository.save(newUserDeviceToken);
        }
    }

    public void deleteByToken(String token) throws IdInvalidException {
        UserDeviceToken userDeviceToken = this.userDeviceTokenRepository.findByToken(token);
        System.out.println(token);
        if (userDeviceToken == null) {
            throw new IdInvalidException("Not found user device token by token: " + token);
        }
        userDeviceTokenRepository.delete(userDeviceToken);
    }

    public List<UserDeviceToken> findByUserId(int userId) throws IdInvalidException {
        User currentUser = userService.fetchUserById(userId);
        if(currentUser == null) {
            throw(new IdInvalidException("User not found"));
        }
        return userDeviceTokenRepository.findByUser(currentUser);
    }
}
