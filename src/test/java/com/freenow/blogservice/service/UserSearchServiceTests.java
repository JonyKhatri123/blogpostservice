package com.freenow.blogservice.service;

import com.freenow.blogservice.exception.UserNotFoundException;
import com.freenow.blogservice.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Search Users APIs")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserSearchService.class})
@SpringBootTest(classes = { RestTemplateAutoConfiguration.class })
public class UserSearchServiceTests {

    @Autowired
    private UserSearchService userSearchService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should find all users from API")
    public void shouldFindAllUsers() {
        List<User> users = userSearchService.findAllUsers();
        assertTrue(!users.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Samantha"})
    @DisplayName("Should Find user by user name")
    public void shouldFindUserByUserName(String userName) {
        Optional<User> user = userSearchService.findUserByUserName(userName);
        assertAll(
                () -> assertTrue(user.isPresent()),
                () -> assertTrue(user.get().getUsername().equalsIgnoreCase(userName)),
                () -> assertNotNull(user.get().getId())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jony"})
    @DisplayName("Should throw user not found exception")
    public void shouldThrowUserNotFoundException(String userName) {
        Optional<User> user = userSearchService.findUserByUserName(userName);
        assertThrows(UserNotFoundException.class,
                        () -> {
                            throw new UserNotFoundException();});
    }

    @ParameterizedTest
    @ValueSource(strings = {"Samantha"})
    @DisplayName("Should return user id from user name")
    public void shouldFindIdByUserName(String userName) {
        Long id = userSearchService.findUserIdByUserName(userName);
        assertTrue(id != -1L);
    }
}
