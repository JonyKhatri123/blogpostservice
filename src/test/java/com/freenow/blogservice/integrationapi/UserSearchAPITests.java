package com.freenow.blogservice.integrationapi;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("User Search API")
public class UserSearchAPITests {

    private ValidatableResponse response;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        response = given().when()
                .get("/users")
                .then();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Samantha"})
    @DisplayName("Should Search User By Name")
    public void shouldSearchUserByName(String userName){
        response
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("[0]", allOf(hasKey("username")))
                .body("username", hasItem(userName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"I am not UserName", "50590904", " ", ""})
    @DisplayName("Invalid User tests")
    public void shouldSearchUserByNameThatIsNotPresent(String userName) {
        ValidatableResponse response = given().when()
                .get("/users").then();
        response.assertThat()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("userName",not(hasItem(userName)));
    }

}
