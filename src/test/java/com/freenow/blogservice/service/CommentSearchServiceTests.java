package com.freenow.blogservice.service;

import com.freenow.blogservice.exception.CommentNotFoundException;
import com.freenow.blogservice.models.Comment;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Search Comments APIs")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CommentSearchService.class, PostSearchService.class, UserSearchService.class})
@SpringBootTest(classes = { RestTemplateAutoConfiguration.class })
public class CommentSearchServiceTests {

    @Autowired
    private CommentSearchService commentSearchService;

    @Autowired
    private PostSearchService postSearchService;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("Should return comment for given user Name.")
    @ParameterizedTest
    @ValueSource(strings = {"Samantha"})
    public void shouldReturnAllComments(String userName) {
        Set<Long> postIds = postSearchService.findPostIdsFromCollectedPosts(
                        postSearchService
                        .findPostByUser(userName));
        List<Comment> comments = commentSearchService.findCommentsByPostIds(postIds);
        assertTrue(!comments.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Samantha1"})
    @DisplayName("Should throw comment not found exception")
    public void shouldThrowCommentNotFoundException(String userName) {
        Set<Long> postIds = postSearchService.findPostIdsFromCollectedPosts(
                postSearchService
                        .findPostByUser(userName));
        List<Comment> comments = commentSearchService.findCommentsByPostIds(postIds);
        assertThrows(CommentNotFoundException.class,
                () -> {
                    throw new CommentNotFoundException();
                });
    }

    @DisplayName("Collect all email addresses from given list of comments")
    @ParameterizedTest
    @ValueSource(strings = {"Samantha"})
    public void shouldReturnAllEmails(String userName) {
        Set<Long> postIds = postSearchService.findPostIdsFromCollectedPosts(
                postSearchService
                        .findPostByUser(userName));
        List<Comment> comments = commentSearchService.findCommentsByPostIds(postIds);
        List<String> emails = comments.stream()
                .map(comment -> comment.getEmail())
                .collect(Collectors.toList());
        assertTrue(!emails.isEmpty());
    }
}
