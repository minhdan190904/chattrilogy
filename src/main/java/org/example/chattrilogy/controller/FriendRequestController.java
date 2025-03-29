package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.domain.dto.FriendRequestDTO;
import org.example.chattrilogy.service.FriendRequestService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("friend_requests")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @GetMapping("/friends")
    public ResponseEntity<List<User>> getAllFriends(@RequestParam int userId) throws IdInvalidException {
        List<User> friends = friendRequestService.getAllFriend(userId);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FriendRequestDTO>> getFriendRequests(@PathVariable int userId) throws IdInvalidException {
        List<FriendRequestDTO> friendRequests = friendRequestService.getFriendRequests(userId);
        return ResponseEntity.ok(friendRequests);
    }

    @PostMapping("/send")
    public ResponseEntity<FriendRequestDTO> sendFriendRequest(@RequestBody FriendRequestDTO friendRequestDTO) throws IdInvalidException {
        friendRequestService.handleUpdateStatusFriendRequest(friendRequestDTO);
        System.out.println("$$$$$" + friendRequestDTO + "$$$$$$$");
        return ResponseEntity.ok(friendRequestDTO);
    }

}
