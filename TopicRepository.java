package com.example.forohub.repository;

import com.example.forohub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndContent(String title, String content);
    List<Topic> findTop10ByOrderByCreatedAtAsc();
    List<Topic> findByCourseAndYear(String course, int year);
}
