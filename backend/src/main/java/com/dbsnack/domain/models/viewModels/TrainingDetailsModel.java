package com.dbsnack.domain.models.viewModels;

import java.util.List;

public class TrainingDetailsModel {

    private long id;

    private String name;

    private String description;

    private String technologyStack;

    private String level;

    private int requiredHours;

    private String image;

    private List<TopicDetailsModel> topics;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<TopicDetailsModel> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDetailsModel> topics) {
        this.topics = topics;
    }
}
