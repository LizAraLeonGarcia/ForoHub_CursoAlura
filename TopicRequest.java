package com.example.forohub.dto;

import javax.validation.constraints.NotBlank;

public class TopicRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String author;

    @NotBlank
    private String course;

    // Getters and Setters
}
