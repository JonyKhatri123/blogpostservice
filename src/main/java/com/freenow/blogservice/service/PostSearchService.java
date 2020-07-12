package com.freenow.blogservice.service;

import com.freenow.blogservice.models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${freenow.blogpost.api.baseurl}")
    public String BASE_URL;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.debug("Find Post by user name");
        Function<String, Long> findUserId = userSearchService :: findUserIdByUserName;
        Long userId = findUserId.apply(userName);
        if(userId == -1) {
            logger.warn("User Id is not found for user name {} ", userName);
            return Collections.emptyList();
        }
        return findPostsByUserId(userId);
    }

    public List<Post> findPostsByUserId(Long userId) {
        logger.debug("Finding posts posted by the given user id {} ", userId);
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
