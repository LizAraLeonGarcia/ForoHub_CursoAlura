package com.example.forohub.controller;

import com.example.forohub.dto.TopicRequest;
import com.example.forohub.dto.TopicResponse;
import com.example.forohub.model.Topic;
import com.example.forohub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<Topic> createTopic(@Valid @RequestBody TopicRequest topicRequest) {
        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setContent(topicRequest.getContent());
        topic.setAuthor(topicRequest.getAuthor());
        topic.setCourse(topicRequest.getCourse());
        return ResponseEntity.ok(topicService.createTopic(topic));
    }

    @GetMapping
    public List<TopicResponse> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/top10")
    public List<TopicResponse> getTop10Topics() {
        return topicService.getTop10Topics();
    }

    @GetMapping("/search")
    public List<TopicResponse> getTopicsByCourseAndYear(@RequestParam String course, @RequestParam int year) {
        return topicService.getTopicsByCourseAndYear(course, year);
    }

    @GetMapping("/paged")
    public Page<TopicResponse> getTopicsPaged(@PageableDefault(size = 10) Pageable pageable) {
        return topicService.getTopicsPaged(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        return topicService.getTopicById(id)
                .map(topic -> ResponseEntity.ok(topicService.convertToResponse(topic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicRequest topicRequest) {
        return topicService.getTopicById(id)
                .map(existingTopic -> {
                    existingTopic.setTitle(topicRequest.getTitle());
                    existingTopic.setContent(topicRequest.getContent());
                    existingTopic.setAuthor(topicRequest.getAuthor());
                    existingTopic.setCourse(topicRequest.getCourse());
                    topicService.updateTopic(existingTopic);
                    return ResponseEntity.ok(topicService.convertToResponse(existingTopic));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        return topicService.getTopicById(id)
                .map(topic -> {
                    topicService.deleteTopic(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
