package com.example.forohub.service;

import com.example.forohub.dto.TopicResponse;
import com.example.forohub.model.Topic;
import com.example.forohub.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic createTopic(Topic topic) {
        if (topicRepository.existsByTitleAndContent(topic.getTitle(), topic.getContent())) {
            throw new IllegalArgumentException("Tópico duplicado");
        }
        return topicRepository.save(topic);
    }

    public List<TopicResponse> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TopicResponse> getTop10Topics() {
        return topicRepository.findTop10ByOrderByCreatedAtAsc().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TopicResponse> getTopicsByCourseAndYear(String course, int year) {
        return topicRepository.findByCourseAndYear(course, year).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Page<TopicResponse> getTopicsPaged(Pageable pageable) {
        return topicRepository.findAll(pageable).map(this::convertToResponse);
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic updateTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    public TopicResponse convertToResponse(Topic topic) {
        TopicResponse response = new TopicResponse();
        response.setTitle(topic.getTitle());
        response.setContent(topic.getContent());
        response.setCreatedAt(topic.getCreatedAt());
        response.setStatus(topic.getStatus());
        response.setAuthor(topic.getAuthor());
        response.setCourse(topic.getCourse());
        return response;
    }
}
