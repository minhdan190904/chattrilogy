package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.UserDeviceToken;
import org.example.chattrilogy.domain.dto.NotificationRequest;
import org.example.chattrilogy.service.FCMService;
import org.example.chattrilogy.service.UserDeviceTokenService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final FCMService fcmService;
    private final UserDeviceTokenService userDeviceTokenService;

    public NotificationController(FCMService fcmService, UserDeviceTokenService userDeviceTokenService) {
        this.fcmService = fcmService;
        this.userDeviceTokenService = userDeviceTokenService;
    }

    @PostMapping("/send/{userId}")
    public ResponseEntity<List<UserDeviceToken>> sendNotification(
            @PathVariable("userId") int userId,
            @RequestBody NotificationRequest notificationRequest
    ) throws IdInvalidException {
        List<UserDeviceToken> userDeviceTokenList = userDeviceTokenService.findByUserId(userId);
        for(UserDeviceToken userDeviceToken : userDeviceTokenList) {
            notificationRequest.setToken(userDeviceToken.getToken());
            try {
                fcmService.sendMessageToToken(notificationRequest);
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        return ResponseEntity.ok(userDeviceTokenList);
    }
}
