package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.dto.FriendRequestDTO;
import org.example.chattrilogy.service.FriendRequestService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<FriendRequestDTO>> getFriendRequests(@PathVariable int userId) throws IdInvalidException {
        List<FriendRequestDTO> friendRequests = friendRequestService.getFriendRequests(userId);
        return ResponseEntity.ok(friendRequests);
    }

    @PostMapping("/friends")
    public ResponseEntity<FriendRequestDTO> createFriendRequest(@RequestBody FriendRequestDTO friendRequestDTO) throws IdInvalidException {
        friendRequestService.handleUpdateStatusFriendRequest(friendRequestDTO);
        return ResponseEntity.ok(friendRequestDTO);
    }

}
