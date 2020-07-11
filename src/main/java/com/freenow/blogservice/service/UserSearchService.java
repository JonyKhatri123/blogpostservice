package com.freenow.blogservice.service;

import com.freenow.blogservice.exception.UserNotFoundException;
import com.freenow.blogservice.models.User;
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

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public List<User> findAllUsers() {
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
        Optional<User> optionalUser = findAllUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(userName))
                .findFirst();
        if(!optionalUser.isPresent()) {
            new UserNotFoundException();
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
