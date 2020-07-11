package com.freenow.blogservice.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Comment POJO Test")
public class CommentTests {

    @ParameterizedTest
    @MethodSource("createCommentJson")
    public void shouldReturnPost(String input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Comment comment = mapper.readValue(input, Comment.class);
        Assertions.assertAll(
                ()->assertNotNull(comment),
                ()->assertEquals(comment.getId(),1),
                ()->assertNotNull(comment.getName()),
                ()->assertNotNull(comment.getEmail()),
                ()->assertNotNull(comment.getBody())
        );
    }

    private static Stream<String> createCommentJson(){
        String commentJson = "{\"postId\":\"1\",\"id\":\"1\",\"name\":\"id labore ex et quam laborum\",\"email\":\"Eliseo@gardner.biz\",\"body\":\"some body\"}";
        return Stream.of(commentJson);
    }

}
