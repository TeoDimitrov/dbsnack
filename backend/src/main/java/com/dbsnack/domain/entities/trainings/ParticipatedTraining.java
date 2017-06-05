package com.dbsnack.domain.entities.trainings;


import com.dbsnack.domain.entities.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participated_trainings")
public class ParticipatedTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @OneToOne
    @JoinColumn(name = "current_participated_lesson_id")
    private ParticipatedLesson currentParticipatedLesson;

    @OneToMany(mappedBy = "participatedTraining", cascade = CascadeType.ALL)
    private List<ParticipatedLesson> participatedLessons;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private User participant;

    @Transient
    private boolean isCompleted;

    public ParticipatedTraining() {
        this.setParticipatedLessons(new ArrayList<>());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public ParticipatedLesson getCurrentParticipatedLesson() {
        return currentParticipatedLesson;
    }

    public void setCurrentParticipatedLesson(ParticipatedLesson currentParticipatedLesson) {
        this.currentParticipatedLesson = currentParticipatedLesson;
    }

    public List<ParticipatedLesson> getParticipatedLessons() {
        return participatedLessons;
    }

    public void setParticipatedLessons(List<ParticipatedLesson> participatedLessons) {
        this.participatedLessons = participatedLessons;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public void addParticipatedLesson(ParticipatedLesson participatedLesson) {
        participatedLesson.setParticipatedTraining(this);
        this.getParticipatedLessons().add(participatedLesson);
        this.setCurrentParticipatedLesson(participatedLesson);
    }

    public ParticipatedLesson goToNextParticipatedLesson() {
        int currentTopicSequence = this.getCurrentParticipatedLesson().getLesson().getTopic().getSequence();
        int currentLessonSequence = this.getCurrentParticipatedLesson().getLesson().getSequence();
        Lesson nextLesson = this.searchNextLesson(currentTopicSequence, currentLessonSequence);
        if (nextLesson == null) {
            this.completeParticipatedTraining();
            return this.getCurrentParticipatedLesson();
        }

        ParticipatedLesson existingParticipatedLesson =
                this.getParticipatedLessons()
                        .stream()
                        .filter(pl -> pl.getLesson() == nextLesson && pl.getParticipatedTraining() == this)
                        .findFirst()
                        .orElse(null);

        if (existingParticipatedLesson != null) {
            return existingParticipatedLesson;
        } else {
            return new ParticipatedLesson(nextLesson);
        }
    }

    public void goToPreviousParticipatedLesson() {
        int currentTopicSequence = this.getCurrentParticipatedLesson().getLesson().getTopic().getSequence();
        int currentLessonSequence = this.getCurrentParticipatedLesson().getLesson().getSequence();
        Lesson previousLesson = this.searchPreviousLesson(currentTopicSequence, currentLessonSequence);
        if (previousLesson == null) {
            return;
        }

        ParticipatedLesson existingParticipatedLesson =
                this.getParticipatedLessons()
                        .stream()
                        .filter(pl -> pl.getLesson() == previousLesson && pl.getParticipatedTraining() == this)
                        .findFirst()
                        .orElse(null);

        if (existingParticipatedLesson != null) {
            this.setCurrentParticipatedLesson(existingParticipatedLesson);
        }
    }

    private Lesson searchNextLesson(int currentTopicSequence, int currentLessonSequence) {
        Lesson nextLesson = null;
        //Check only current topic
        for (Topic topic : this.getTraining().getTopics()) {
            if (topic.getSequence() != currentTopicSequence) {
                continue;
            }

            //Check all lessons to find next
            int minSequenceDifference = Integer.MAX_VALUE;
            for (Lesson lesson : topic.getLessons()) {
                if(lesson.getSequence() <= currentLessonSequence){
                    continue;
                }

                int currentSequenceDifference = lesson.getSequence() - currentLessonSequence;
                if (currentSequenceDifference < minSequenceDifference) {
                    nextLesson = lesson;
                    minSequenceDifference = currentSequenceDifference;
                }
            }

            if (nextLesson == null) {
                //No lesson then check next topic
                currentTopicSequence++;
                final int nextTopicSequence = currentTopicSequence;
                Topic nextTopic = this.getTraining()
                        .getTopics()
                        .stream()
                        .filter(t -> t.getSequence() == nextTopicSequence)
                        .findFirst()
                        .orElse(null);

                //Check if there is a next topic
                if (nextTopic == null) {
                    return null;
                }

                //Recursively search the next topic
                nextLesson = searchNextLesson(nextTopicSequence, 0);
            }

            break;
        }

        return nextLesson;
    }

    private Lesson searchPreviousLesson(int currentTopicSequence, int currentLessonSequence) {
        Lesson previousLesson = null;
        //Check only current topic
        for (Topic topic : this.getTraining().getTopics()) {
            if (topic.getSequence() != currentTopicSequence) {
                continue;
            }

            //Check all lessons to find previous
            int minSequenceDifference = Integer.MIN_VALUE;
            for (Lesson lesson : topic.getLessons()) {
                if (lesson.getSequence() >= currentLessonSequence) {
                    continue;
                }

                int currentSequenceDifference = lesson.getSequence() - currentLessonSequence;
                if (currentSequenceDifference > minSequenceDifference) {
                    previousLesson = lesson;
                    minSequenceDifference = currentSequenceDifference;
                }
            }

            if (previousLesson == null) {
                //No lesson then check previous topic
                currentTopicSequence--;
                final int previousTopicSequence = currentTopicSequence;
                currentLessonSequence = Integer.MAX_VALUE;

                //Check if there is a previous topic
                if (previousTopicSequence <= 0) {
                    return null;
                }

                //Recursively search the previous topic
                previousLesson = searchPreviousLesson(previousTopicSequence, currentLessonSequence);
            }

            break;
        }

        return previousLesson;
    }

    private void completeParticipatedTraining() {
        this.setCompleted(true);
    }
}
