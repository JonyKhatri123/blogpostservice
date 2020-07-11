package com.freenow.blogservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    Long postId;
    Long id;
    String name;
    String email;
    String body;
}
