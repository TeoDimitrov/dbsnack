package com.dbsnack.domain.models.bandingModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

public class TopicEditorModel {

    private long id;

    @Size(min = 5, max = 20, message = "The name should be between 5 and 20 symbols")
    private String name;

    @JsonProperty("isActive")
    private boolean isActive;

    @Size(min = 10, max = 200, message = "The description should be between 10 and 200 symbols")
    private String description;

    @Min(value = 1, message = "The required hours should be between 1 and 100 symbols")
    @Max(value = 99, message = "The required hours should be between 1 and 100 symbols")
    private int sequence;

    private List<LessonEditorModel> lessons;

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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<LessonEditorModel> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonEditorModel> lessons) {
        this.lessons = lessons;
    }
}
