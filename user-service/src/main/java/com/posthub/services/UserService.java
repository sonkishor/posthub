package com.posthub.services;

import com.posthub.entities.Post;
import com.posthub.entities.User;
import com.posthub.exceptions.custom.IDNotFoundException;
import com.posthub.external.service.PostService;
import com.posthub.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PostService postService;

    public UserService(UserRepository userRepository, PostService postService) {
        this.userRepository = userRepository;
        this.postService = postService;
    }

    public User saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return this.userRepository.save(user);
    }

    public User getUsers(String id) {
        User user = this.userRepository
                .findById(id)
                .orElseThrow(() ->
                        new IDNotFoundException("User not found exception!!"));
        List<Post> posts = this.postService.getPostsByUserId(user.getId());
        user.setPosts(posts);
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = this.userRepository.findAll();
       return users
                .stream()
                .peek(user -> {
                    List<Post> posts = this.postService.getPostsByUserId(user.getId());
                    user.setPosts(posts);
                })
               .collect(Collectors.toList());
    }

    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }

    public User updateUser(String id, User updatedData) {
        User user = this.getUsers(id);
        user.setAge(updatedData.getAge());
        user.setGender(updatedData.getGender());
        user.setAge(updatedData.getAge());
        return this.userRepository.save(user);
    }
}
