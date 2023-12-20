package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.service.impl.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllPosts() {
        return postsService.getAllPosts();
    }

    @GetMapping( value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPostById(@PathVariable Long id) {
        return postsService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Posts post){
        return postsService.createPosts(post);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Posts>> getAllPostsDB(){
        return postsService.getAllPost();
    }

}
