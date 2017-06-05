package com.dbsnack.services.training.implementation;

import com.dbsnack.domain.entities.trainings.Level;
import com.dbsnack.domain.entities.trainings.TechnologyStack;
import com.dbsnack.domain.entities.trainings.Topic;
import com.dbsnack.domain.entities.trainings.Training;
import com.dbsnack.domain.entities.users.User;
import com.dbsnack.domain.models.bandingModels.TopicEditorModel;
import com.dbsnack.domain.models.bandingModels.TrainingEditorModel;
import com.dbsnack.domain.models.viewModels.TrainingActiveModel;
import com.dbsnack.domain.models.viewModels.TrainingDetailsModel;
import com.dbsnack.exception.training.TrainingDoesNotExistException;
import com.dbsnack.repositories.training.TrainingRepository;
import com.dbsnack.services.training.TrainingService;
import com.dbsnack.services.user.StandardUserService;
import com.dbsnack.utils.TrainingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final ModelMapper modelMapper;

    private final TrainingRepository trainingRepository;

    private final StandardUserService standardUserService;

    @Autowired
    public TrainingServiceImpl(ModelMapper modelMapper,
                               TrainingRepository trainingRepository,
                               StandardUserService standardUserService) {
        this.modelMapper = modelMapper;
        this.trainingRepository = trainingRepository;
        this.standardUserService = standardUserService;
    }

    @Override
    public void save(TrainingEditorModel trainingEditorModel, String username) {
        Training training = this.modelMapper.map(trainingEditorModel, Training.class);
        User user = this.standardUserService.getUserByUsername(username);
        training.setAuthor(user);
        training.setCreatedOn(new Date());
        training.setImage(this.getImageSource(trainingEditorModel.getTechnologyStack()));

        this.trainingRepository.save(training);
    }

    @Override
    @Transactional
    public void update(TrainingEditorModel trainingEditorModel, long trainingId) {
        Training training = this.trainingRepository.findOne(trainingId);
        training.setName(trainingEditorModel.getName());
        training.setDescription(trainingEditorModel.getDescription());
        training.setActive(trainingEditorModel.isActive());
        training.setLevel(Level.valueOf(trainingEditorModel.getLevel().toUpperCase()));
        training.setTechnologyStack(TechnologyStack.valueOf(trainingEditorModel.getTechnologyStack().toUpperCase()));
        training.setRequiredHours(trainingEditorModel.getRequiredHours());
        training.setImage(this.getImageSource(trainingEditorModel.getTechnologyStack()));
    }

    @Override
    @Transactional
    public void addTopic(TopicEditorModel topicEditorModel, long trainingId) {
        Training training = this.trainingRepository.findOne(trainingId);
        Topic newTopic = this.modelMapper.map(topicEditorModel, Topic.class);
        newTopic.setCreatedOn(new Date());
        newTopic.setTraining(training);
        training.addTopic(newTopic);
    }

    @Override
    public List<TrainingActiveModel> getAllActiveTrainings() {
        List<Training> trainings = this.trainingRepository.findAllActiveTrainings();
        List<TrainingActiveModel> models = new ArrayList<>();
        for (Training training : trainings) {
            TrainingActiveModel model = this.modelMapper.map(training, TrainingActiveModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public TrainingDetailsModel getTrainingDetails(long id) {
        Training training = this.trainingRepository.findActiveTrainingWithActiveTopicsWithActiveLessonsById(id);
        if (training == null) {
            throw new TrainingDoesNotExistException(TrainingUtils.TRAINING_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }

        TrainingDetailsModel trainingDetailsModel = this.modelMapper.map(training, TrainingDetailsModel.class);
        return trainingDetailsModel;
    }

    @Override
    public List<TrainingEditorModel> findAllTrainingsByAuthorOnEditorPage(String email) {
        List<Training> trainings = this.trainingRepository.findAllByAuthor(email);
        List<TrainingEditorModel> models = new ArrayList<>();
        for (Training training : trainings) {
            TrainingEditorModel model = this.modelMapper.map(training, TrainingEditorModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public List<TrainingEditorModel> findAllTrainingsOnEditorPage() {
        List<Training> trainings = this.trainingRepository.findAll();
        List<TrainingEditorModel> models = new ArrayList<>();
        for (Training training : trainings) {
            TrainingEditorModel model = this.modelMapper.map(training, TrainingEditorModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public Training getTrainingById(long trainingId) {
        return this.trainingRepository.findOne(trainingId);
    }

    private String getImageSource(String technologyStack) {
        return TrainingUtils.TRAINING_IMAGE_SRC + technologyStack.toLowerCase() + ".png";
    }
}
