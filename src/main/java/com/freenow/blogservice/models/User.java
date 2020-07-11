package com.freenow.blogservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    Long id;
    String name;
    String username;
    String email;
    Address address;
    String phone;
    String website;
    Company company;
}
