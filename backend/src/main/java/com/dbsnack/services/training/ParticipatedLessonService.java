package com.dbsnack.services.training;

import com.dbsnack.domain.entities.trainings.ParticipatedLesson;
import com.dbsnack.domain.models.viewModels.ParticipatedTrainingLearnModel;

public interface ParticipatedLessonService {

    void save(ParticipatedLesson participatedLesson);

    ParticipatedLesson findOneById(long id);

    void setSubmission(long participatedTrainingId, String sqlStatement);

    void complete(long participatedTrainingId);
}
