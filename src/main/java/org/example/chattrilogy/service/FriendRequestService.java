package org.example.chattrilogy.service;

import org.example.chattrilogy.domain.Chat;
import org.example.chattrilogy.domain.FriendRequest;
import org.example.chattrilogy.domain.RequestStatus;
import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.domain.dto.FriendRequestDTO;
import org.example.chattrilogy.repository.ChatRepository;
import org.example.chattrilogy.repository.FriendRequestRepository;
import org.example.chattrilogy.repository.UserRepository;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    private final ChatRepository chatRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserService userService, ChatRepository chatRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userService = userService;
        this.chatRepository = chatRepository;
    }

    public void handleUpdateStatusFriendRequest(FriendRequestDTO friendRequestDTO) throws IdInvalidException {
        User sender = userService.fetchUserById(friendRequestDTO.getSender().getId());
        User receiver = userService.fetchUserById(friendRequestDTO.getReceiver().getId());

        FriendRequest existingFriendRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (existingFriendRequest == null) {
            existingFriendRequest = friendRequestRepository.findBySenderAndReceiver(receiver, sender);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String now = sdf.format(new Date());


        if (existingFriendRequest == null) {
            existingFriendRequest = new FriendRequest(sender, receiver, friendRequestDTO.getRequestStatus(), now);
        } else {
            existingFriendRequest.setRequestStatus(friendRequestDTO.getRequestStatus());
            existingFriendRequest.setTimeSend(now);
            existingFriendRequest.setSender(sender);
            existingFriendRequest.setReceiver(receiver);
        }

        if (friendRequestDTO.getRequestStatus() == RequestStatus.ACCEPTED) {
            if (!chatRepository.existsByUser1AndUser2(sender, receiver) &&
                    !chatRepository.existsByUser1AndUser2(receiver, sender)) {
                chatRepository.save(new Chat(sender, receiver));
            }
        }

        friendRequestRepository.save(existingFriendRequest);
    }

    public List<User> getAllFriend(int userId) throws IdInvalidException {
        List<FriendRequestDTO> currentFriendRequestDTOs = getFriendRequests(userId);
        if (currentFriendRequestDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        return currentFriendRequestDTOs
                .stream()
                .filter(friendRequestDTO -> friendRequestDTO.getRequestStatus() == RequestStatus.ACCEPTED)
                .map(friendRequestDTO -> friendRequestDTO.getSender().getId() == userId
                        ? friendRequestDTO.getReceiver()
                        : friendRequestDTO.getSender())
                .collect(Collectors.toList());
    }

    public List<FriendRequestDTO> getFriendRequests(int userId) throws IdInvalidException {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByUserId(userId);

        List<FriendRequestDTO> friendRequestDTOs = friendRequests.stream()
                .map(request -> new FriendRequestDTO(
                        request.getSender(),
                        request.getReceiver(),
                        request.getRequestStatus()))
                .collect(Collectors.toList());

        User currentUser = userService.fetchUserById(userId);

        Set<String> existingPairs = friendRequests.stream()
                .flatMap(request -> Stream.of(
                        request.getSender().getId() + "-" + request.getReceiver().getId(),
                        request.getReceiver().getId() + "-" + request.getSender().getId()))
                .collect(Collectors.toSet());

        List<FriendRequestDTO> missingRequests = userService.getAllUser().stream()
                .filter(user -> user.getId() != userId)
                .filter(user -> !existingPairs.contains(userId + "-" + user.getId())
                        && !existingPairs.contains(user.getId() + "-" + userId))
                .map(user -> new FriendRequestDTO(currentUser, user, RequestStatus.REJECTED))
                .toList();

        friendRequestDTOs.addAll(missingRequests);
        return friendRequestDTOs;
    }
}
