package com.afsar.task_manager.service;

import com.afsar.task_manager.entity.User;
import com.afsar.task_manager.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public boolean findUserById(ObjectId id){

        Optional<User> id1 = userRepository.findById(id);
        return id1.isPresent();
    }

    public void deleteUserById(ObjectId id){
            userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
    public void deleteByUserName(String username){
         userRepository.deleteByUserName(username);
    }

}
