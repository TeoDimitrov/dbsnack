package com.dbsnack.services.training;

import com.dbsnack.domain.entities.trainings.Lesson;
import com.dbsnack.domain.models.viewModels.ParticipatedTrainingLearnModel;

public interface ParticipatedTrainingService {

    ParticipatedTrainingLearnModel learn(long trainingId, String username);

    ParticipatedTrainingLearnModel getNextLesson(long participatedTrainingId);

    ParticipatedTrainingLearnModel getPreviousLesson(long participatedTrainingId);

}
