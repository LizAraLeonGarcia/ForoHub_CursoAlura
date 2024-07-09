package com.example.forohub.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    private String course;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String status = "OPEN";

    // Getters and Setters
}
