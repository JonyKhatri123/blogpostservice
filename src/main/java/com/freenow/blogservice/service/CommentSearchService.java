package com.freenow.blogservice.service;

import com.freenow.blogservice.exception.CommentNotFoundException;
import com.freenow.blogservice.models.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate createRestTemplate() {
        return restTemplateBuilder
                .rootUri(BASE_URL)
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<Comment> findCommentsByPostIds(Set<Long> postIds) {
        logger.debug("Find comments by post ids ");
        RestTemplate restTemplate = createRestTemplate();
        List<Comment> comments = Stream.of(restTemplate
                .getForEntity("/comments", Comment[].class)
                .getBody())
                .filter(comment -> postIds.contains(comment.getPostId()))
                .collect(toList());
        if(comments.isEmpty()) {
            new CommentNotFoundException();
            logger.error("ERROR: Comments not found for given post IDs {} ", postIds);
            return Collections.emptyList();
        }
        return comments;
    }

    public List<String> findEmailsFromComments(List<Comment> comments) {
        logger.debug("Extracting emails from comments");
        return comments.stream()
                .map(Comment::getEmail)
                .collect(toList());
    }
}
