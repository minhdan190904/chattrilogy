package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.domain.UserDeviceToken;
import org.example.chattrilogy.service.UserDeviceTokenService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_device_tokens")
public class UserDeviceTokenController {
    private final UserDeviceTokenService userDeviceTokenService;

    public UserDeviceTokenController(UserDeviceTokenService userDeviceTokenService) {
        this.userDeviceTokenService = userDeviceTokenService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDeviceToken> createUserDeviceToken(@RequestBody UserDeviceToken userDeviceToken) {
        UserDeviceToken createdUserDeviceToken = this.userDeviceTokenService.createUserDeviceToken(userDeviceToken);
        return new ResponseEntity<>(createdUserDeviceToken, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteByToken(@RequestParam String token) throws IdInvalidException {
        userDeviceTokenService.deleteByToken(token);
        return ResponseEntity.ok(200);
    }
}
