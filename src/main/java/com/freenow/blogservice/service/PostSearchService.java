package com.freenow.blogservice.service;

import com.freenow.blogservice.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostSearchService {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate createRestTemplate() {
        return restTemplateBuilder
                .rootUri(BASE_URL)
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<Post> findPostByUser(String userName) {
        Function<String, Long> findUserId = userSearchService :: findUserIdByUserName;
        Long userId = findUserId.apply(userName);
        if(userId == -1) {
            return Collections.emptyList();
        }
        return findPostsByUserId(userId);
    }

    public List<Post> findPostsByUserId(Long userId) {
        RestTemplate restTemplate = createRestTemplate();
        return Stream.of(restTemplate
                .getForEntity("/posts", Post[].class)
                .getBody()).filter(post-> post.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public Set<Long> findPostIdsFromCollectedPosts(List<Post> postByUser) {
        return postByUser.stream()
                .mapToLong(Post::getId)
                .boxed()
                .collect(Collectors.toSet());
    }
}
