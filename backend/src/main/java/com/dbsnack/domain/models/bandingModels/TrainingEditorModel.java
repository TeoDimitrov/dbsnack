package com.dbsnack.domain.models.bandingModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class TrainingEditorModel {

    private long id;

    @Size(min = 5, max = 20, message = "The name should be between 5 and 20 symbols")
    private String name;

    @JsonProperty("isActive")
    private boolean isActive;

    @Size(min = 10, max = 200, message = "The description should be between 10 and 200 symbols")
    private String description;

    @NotNull(message = "The technology stack is required")
    private String technologyStack;

    @NotNull(message = "The level is required")
    private String level;

    @Min(value = 1, message = "The required hours should be between 1 and 100 symbols")
    @Max(value = 99, message = "The required hours should be between 1 and 100 symbols")
    private int requiredHours;

    private String authorUsername;

    private List<TopicEditorModel> topics;

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

    public String getTechnologyStack() {
        return technologyStack;
    }

    public void setTechnologyStack(String technologyStack) {
        this.technologyStack = technologyStack;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(int requiredHours) {
        this.requiredHours = requiredHours;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public List<TopicEditorModel> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicEditorModel> topics) {
        this.topics = topics;
    }
}
