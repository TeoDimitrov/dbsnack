package com.dbsnack.services.training;

import com.dbsnack.domain.entities.trainings.Training;
import com.dbsnack.domain.models.bandingModels.TopicEditorModel;
import com.dbsnack.domain.models.bandingModels.TrainingEditorModel;
import com.dbsnack.domain.models.viewModels.TrainingActiveModel;
import com.dbsnack.domain.models.viewModels.TrainingDetailsModel;

import java.util.List;

public interface TrainingService {

    void save(TrainingEditorModel trainingEditorModel, String username);

    void update(TrainingEditorModel trainingEditorModel, long trainingId);

    void addTopic(TopicEditorModel topicEditorModel, long trainingId);

    List<TrainingActiveModel> getAllActiveTrainings();

    TrainingDetailsModel getTrainingDetails(long id);

    List<TrainingEditorModel> findAllTrainingsByAuthorOnEditorPage(String username);

    List<TrainingEditorModel> findAllTrainingsOnEditorPage();

    Training getTrainingById(long trainingId);
}
