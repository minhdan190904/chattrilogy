package org.example.chattrilogy.service;

import org.example.chattrilogy.domain.Chat;
import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.repository.ChatRepository;
import org.example.chattrilogy.repository.UserRepository;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
            return this.userRepository.save(user);
        }
        return null;
    }

    public User getUserByUserName(String userName){
        return this.userRepository.findByEmail(userName);
    }

}
