package com.dbsnack.domain.entities.trainings;

import javax.persistence.*;

@Entity
@Table(name = "participated_lessons")
public class ParticipatedLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "last_submission")
    private String lastSubmission;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "participated_training")
    private ParticipatedTraining participatedTraining;

    @Column(name = "is_completed")
    private boolean isCompleted;

    public ParticipatedLesson() {
    }

    public ParticipatedLesson(Lesson lesson) {
        this.lesson = lesson;
    }

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

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public ParticipatedTraining getParticipatedTraining() {
        return participatedTraining;
    }

    public void setParticipatedTraining(ParticipatedTraining participatedTraining) {
        this.participatedTraining = participatedTraining;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
