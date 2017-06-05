package com.dbsnack.domain.models.viewModels;

public class ParticipatedTrainingLearnModel {

    private long id;

    private boolean isCompleted;

    private ParticipatedLessonLearnModel currentParticipatedLesson;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParticipatedLessonLearnModel getCurrentParticipatedLesson() {
        return currentParticipatedLesson;
    }

    public void setCurrentParticipatedLesson(ParticipatedLessonLearnModel currentParticipatedLesson) {
        this.currentParticipatedLesson = currentParticipatedLesson;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
