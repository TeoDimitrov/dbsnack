package com.dbsnack.domain.models.viewModels;

import java.util.List;

public class TopicDetailsModel {

    private String name;

    private String description;

    private int sequence;

    private List<LessonDetailsModel> lessons;

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

    public List<LessonDetailsModel> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDetailsModel> lessons) {
        this.lessons = lessons;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
