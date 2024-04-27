package com.posthub.services;

import com.posthub.entities.Post;
import com.posthub.exceptions.custom.ResourceNotFoundException;
import com.posthub.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final LoggersEndpoint loggersEndpoint;

    public PostService(PostRepository postRepository, LoggersEndpoint loggersEndpoint) {
        this.postRepository = postRepository;
        this.loggersEndpoint = loggersEndpoint;
    }

    public Post savePosts(Post post) {
        post.setId(UUID.randomUUID().toString());
        return this.postRepository.save(post);
    }

    public Post getPosts(String id) {
        return this.postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found on server!!"));
    }

    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    public List<Post> getAllPostsByUserId(String id) {
        return this.postRepository.findPostsByUserId(id);
    }

    public Post updatePosts(Post post, String id) {
        Post dbPost = this.getPosts(id);
        dbPost.setName(post.getName());
        dbPost.setDescription(post.getDescription());
        return this.postRepository.save(dbPost);
    }

    public boolean checkPosts() {
        List<Post> dbPost = this.getAllPosts();
        System.out.println(dbPost);
        if (dbPost.isEmpty()) {
            return false;
        }
        else {
            return true;
        }

    }
    public void deletePosts(String id) {
        this.getPosts(id);
        this.postRepository.deleteById(id);
    }
}
