package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    @Value("${api.endpoint.url.post}")
    private String BASE_URL;

    private final RestTemplate restTemplate;

    private final PostsRepository postsRepository;

    public ResponseEntity<String> getAllPosts() {
        return responseMethod(restTemplate.getForEntity(BASE_URL, String.class), "Failed to load data");
    }

    public ResponseEntity<String> getPostById(Long id) {
        return responseMethod(restTemplate.getForEntity(BASE_URL + "/" + id, String.class), "Failed to load data");
    }


    public ResponseEntity<String> createPosts(Posts posts) {
        //Mengatur header permintaan
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Membungkus data permintaan dalam HttpEntity
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts, headers);
        //response
        postsRepository.save(posts);
        return responseMethod(restTemplate.postForEntity(BASE_URL, requestEntity, String.class), "Failed to create data");
    }

    public ResponseEntity<List<Posts>> getAllPost() {
        ResponseEntity<Posts[]> apiResponse = restTemplate.getForEntity(BASE_URL, Posts[].class);
        List<Posts> externalPosts = List.of(apiResponse.getBody());

        List<Posts> dbPosts = postsRepository.findAll();

        //combine the result
        dbPosts.addAll(externalPosts);

        //return the combined list
        return ResponseEntity.ok(dbPosts);
    }

    private ResponseEntity<String> responseMethod(ResponseEntity<String> restTemplate, String message) {
        ResponseEntity<String> responseEntity = restTemplate;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            return ResponseEntity.ok(responseBody);
        }
        return ResponseEntity.status(responseEntity.getStatusCode()).body(message);
    }
}
