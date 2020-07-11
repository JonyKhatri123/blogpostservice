package com.freenow.blogservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    String Street;
    String suite;
    String city;
    String zipcode;
    Geo geo;
}
