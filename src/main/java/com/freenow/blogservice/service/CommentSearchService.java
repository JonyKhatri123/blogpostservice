package com.freenow.blogservice.service;

import com.freenow.blogservice.exception.CommentNotFoundException;
import com.freenow.blogservice.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class CommentSearchService {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate createRestTemplate() {
        return restTemplateBuilder
                .rootUri(BASE_URL)
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<Comment> findCommentsByPostIds(Set<Long> postIds) {
        RestTemplate restTemplate = createRestTemplate();
        List<Comment> comments = Stream.of(restTemplate
                .getForEntity("/comments", Comment[].class)
                .getBody())
                .filter(comment -> postIds.contains(comment.getPostId()))
                .collect(toList());
        if(comments.isEmpty()) {
            new CommentNotFoundException();
            return Collections.emptyList();
        }
        return comments;
    }

    public List<String> findEmailsFromComments(List<Comment> comments) {
        return comments.stream()
                .map(Comment::getEmail)
                .collect(toList());
    }
}
