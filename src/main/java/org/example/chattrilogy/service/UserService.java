package org.example.chattrilogy.service;

import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.repository.UserRepository;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handleCreateUser(User user) throws DataIntegrityViolationException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        userRepository.save(user);
    }


    public void handleDeleteUser(int idUser) throws IdInvalidException {
        User user = this.fetchUserById(idUser);
        if(user == null){
            throw new IdInvalidException("Not exist user with id: " + idUser);
        }
        this.userRepository.deleteById(idUser);
    }

    public User fetchUserById(int idUser) throws IdInvalidException{
        User user = userRepository.findById(idUser).orElse(null);
        if(user == null){
            throw new IdInvalidException("Not exist user with id: " + idUser);
        }
        return user;
    }

    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }

    public User updateUser(User user) throws IdInvalidException{
        User currentUser = this.fetchUserById(user.getId());
        User existingUser = userRepository.findByEmail(user.getEmail());
        if(currentUser != null){
            if(existingUser != null && existingUser.getId() != user.getId()){
                throw new IdInvalidException("Email already exists");
            }
            currentUser.setBirthday(user.getBirthday());
            currentUser.setEmail(user.getEmail());
            currentUser.setName(user.getName());
            currentUser.setPassword(user.getPassword());
            currentUser.setLastSeen(user.getLastSeen());
            currentUser.setIsOnline(user.getIsOnline());
            currentUser.setProfileImageUrl(user.getProfileImageUrl());
            return this.userRepository.save(currentUser);
        }
        return null;
    }

    public void updateStatus(int userId, boolean statusUSer) throws IdInvalidException{
        User currentUser = this.fetchUserById(userId);
        if(currentUser != null){
            currentUser.setIsOnline(statusUSer);
            currentUser.setLastSeen(new Date());
            this.userRepository.save(currentUser);
        }
    }

    public User getUserByUserName(String userName){
        return this.userRepository.findByEmail(userName);
    }
}
