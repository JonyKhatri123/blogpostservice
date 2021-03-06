package com.freenow.blogservice.integrationapi;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasKey;

@DisplayName("Post Search API Test")
public class PostSearchAPITests {

    private ValidatableResponse response;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        response = given().when()
                .get("/posts")
                .then();
    }

    @Test
    @DisplayName("Should Search post")
    public void shouldSearchAllPosts(){
        response
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("[0]",
                        allOf(hasKey("userId"),
                            hasKey("id"),
                            hasKey("title"),
                            hasKey("body")));
    }

}
