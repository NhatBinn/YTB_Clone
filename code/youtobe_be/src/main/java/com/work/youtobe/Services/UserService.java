package com.work.youtobe.Services;

import org.springframework.stereotype.Service;

import com.work.youtobe.Models.Comment;
import com.work.youtobe.Models.User;
import com.work.youtobe.Repositorys.UserRepository;

@Service
public class UserService {
     private final UserRepository userRepository;

     public UserService(UserRepository userRepository) {
          this.userRepository = userRepository;
     }

     public User getUserById(Long userId) {
          return userRepository.findById(userId).orElse(null);
     }

     public void addComment(User user, Comment comment) {
          user.addComment(comment);
          userRepository.save(user);
     }

     public void removeComment(User user, Comment comment) {
          user.removeComment(comment);
          userRepository.save(user);
     }
}
