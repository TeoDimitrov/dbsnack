package com.dbsnack.domain.models.viewModels;

import com.dbsnack.domain.entities.trainings.Lesson;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipatedLessonLearnModel {

    private long id;

    private String lastSubmission;

    private LessonLearnModel lesson;

    private boolean isCompleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastSubmission() {
        return lastSubmission;
    }

    public void setLastSubmission(String lastSubmission) {
        this.lastSubmission = lastSubmission;
    }

    public LessonLearnModel getLesson() {
        return lesson;
    }

    public void setLesson(LessonLearnModel lesson) {
        this.lesson = lesson;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
