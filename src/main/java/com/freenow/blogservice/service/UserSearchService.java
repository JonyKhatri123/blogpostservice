package com.freenow.blogservice.service;

import com.freenow.blogservice.exception.UserNotFoundException;
import com.freenow.blogservice.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserSearchService {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public List<User> findAllUsers() {
        LOGGER.info("Finding all users");
        RestTemplate restTemplate = createRestTemplate();
        return Arrays.asList(restTemplate
                .getForEntity("/users", User[].class)
                .getBody());
    }

    private RestTemplate createRestTemplate() {
        return restTemplateBuilder
                .rootUri(BASE_URL)
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    public Optional<User> findUserByUserName(String userName) {
        LOGGER.info("Finding user by user name");
        Optional<User> optionalUser = findAllUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(userName))
                .findFirst();
        if(!optionalUser.isPresent()) {
            new UserNotFoundException();
            LOGGER.error("ERROR: User Not Found {} ", userName);
        }
        return optionalUser;
    }

    public Long findUserIdByUserName(String userName) {
        Optional<User> optionalUser = findUserByUserName(userName);
        if(optionalUser.isPresent()) {
            return optionalUser.get().getId();
        }
        return -1L;
    }
}
