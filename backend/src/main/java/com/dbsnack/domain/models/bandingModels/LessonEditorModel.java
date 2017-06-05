package com.dbsnack.domain.models.bandingModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class LessonEditorModel {

    private long id;

    @Size(min = 5, max = 20, message = "The name should be between 5 and 20 symbols")
    private String name;

    @JsonProperty("isActive")
    private boolean isActive;

    @Size(min = 10, max = 200, message = "The description should be between 10 and 200 symbols")
    private String description;

    @Size(min = 10, max = 300, message = "The content should be between 10 and 300 symbols")
    private String content;

    @Size(min = 10, max = 300, message = "The exercise should be between 10 and 300 symbols")
    private String exercise;

    @Size(max = 5000, message = "The answer should be between up to 5000 symbols")
    private String answer;

    @Size(max = 5000, message = "The answer verification query should be between up to 5000 symbols")
    private String answerVerificationQuery;

    @Min(value = 1, message = "The required hours should be between 1 and 100 symbols")
    @Max(value = 99, message = "The required hours should be between 1 and 100 symbols")
    private int sequence;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
