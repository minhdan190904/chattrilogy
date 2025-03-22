package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.UserDeviceToken;
import org.example.chattrilogy.service.UserDeviceTokenService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_device_token")
public class UserDeviceTokenController {
    private final UserDeviceTokenService userDeviceTokenService;

    public UserDeviceTokenController(UserDeviceTokenService userDeviceTokenService) {
        this.userDeviceTokenService = userDeviceTokenService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDeviceToken> createUserDeviceToken(@RequestBody UserDeviceToken userDeviceToken) throws IdInvalidException {
        UserDeviceToken createdUserDeviceToken = this.userDeviceTokenService.createUserDeviceToken(userDeviceToken);
        return new ResponseEntity<>(createdUserDeviceToken, HttpStatus.CREATED);
    }
}
