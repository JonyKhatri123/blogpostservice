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

@DisplayName("Post POJO Test")
public class PostTests {
    @ParameterizedTest
    @MethodSource("createPostJson")
    public void shouldReturnPost(String input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(input, Post.class);
        Assertions.assertAll(
                ()->assertNotNull(post),
                ()->assertEquals(post.userId,1),
                ()->assertEquals(post.id,1),
                ()->assertNotNull(post.getBody()),
                ()->assertNotNull(post.getTitle())
        );
    }

    private static Stream<String> createPostJson(){
        String postJson = "{\"userId\":\"1\",\"id\":\"1\",\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto\"}";
        return Stream.of(postJson);
    }

}
