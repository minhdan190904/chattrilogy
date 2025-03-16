package org.example.chattrilogy.repository;

import org.example.chattrilogy.domain.FriendRequest;
import org.example.chattrilogy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    @Query("SELECT f FROM FriendRequest f WHERE f.sender.id = :userId OR f.receiver.id = :userId")
    List<FriendRequest> findAllByUserId(@Param("userId") int userId);

    boolean existsBySenderAndReceiver(User sender, User receiver);

    FriendRequest findBySenderAndReceiver(User sender, User receiver);

}
