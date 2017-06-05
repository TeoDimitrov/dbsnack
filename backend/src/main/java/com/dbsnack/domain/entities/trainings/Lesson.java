package com.dbsnack.domain.entities.trainings;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "sequence")
    private int sequence;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "exercise", columnDefinition = "TEXT")
    private String exercise;

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @Column(name = "answer_verification_query", columnDefinition = "TEXT")
    private String answerVerificationQuery;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "is_active")
    private boolean isActive;

    public Lesson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerVerificationQuery() {
        return answerVerificationQuery;
    }

    public void setAnswerVerificationQuery(String answerVerificationQuery) {
        this.answerVerificationQuery = answerVerificationQuery;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}