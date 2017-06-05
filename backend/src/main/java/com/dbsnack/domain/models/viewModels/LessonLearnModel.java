package com.dbsnack.domain.models.viewModels;

public class LessonLearnModel {

    private String name;

    private String content;

    private String exercise;

    private TopicLearnModel topic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public TopicLearnModel getTopic() {
        return topic;
    }

    public void setTopic(TopicLearnModel topic) {
        this.topic = topic;
    }
}
