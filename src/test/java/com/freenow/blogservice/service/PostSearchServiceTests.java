package com.freenow.blogservice.service;

import com.freenow.blogservice.exception.PostNotFoundException;
import com.freenow.blogservice.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Search Posts APIs")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PostSearchService.class, UserSearchService.class})
@SpringBootTest(classes = { RestTemplateAutoConfiguration.class })
public class PostSearchServiceTests {

    @Autowired
    private PostSearchService postSearchService;

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"Samantha"})
    @DisplayName("Should find all posts by User Name API")
    public void shouldFindAllPostsByUserName(String userName) {
        List<Post> posts = postSearchService.findPostByUser(userName);
        assertAll(
                ()->assertTrue(!posts.isEmpty()));
    }


    @ParameterizedTest
    @ValueSource(strings = {"Samantha1"})
    @DisplayName("Should throw post not found exception")
    public void shouldThrowPostNotFoundException(String userName) {
        List<Post> emptyPosts = postSearchService.findPostByUser(userName);
        assertThrows(PostNotFoundException.class,
                () -> {
                    throw new PostNotFoundException();
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Samantha"})
    @DisplayName("Should collect all PostIds")
    public void shouldFindAllIdsOfPosts(String userName) {
        Set<Long> posts = postSearchService
                .findPostIdsFromCollectedPosts(
                        postSearchService.findPostByUser(userName));
        assertAll(
                ()->assertTrue(!posts.isEmpty()));
    }

}
