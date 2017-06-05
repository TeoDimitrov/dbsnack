package com.dbsnack.services.training.implementation;

import com.dbsnack.domain.entities.trainings.*;
import com.dbsnack.domain.entities.users.User;
import com.dbsnack.domain.models.viewModels.ParticipatedTrainingLearnModel;
import com.dbsnack.exception.training.LessonsNotFoundException;
import com.dbsnack.exception.training.ParticipatedTrainingDoesNotExistException;
import com.dbsnack.exception.training.TopicsNotFoundException;
import com.dbsnack.exception.training.TrainingDoesNotExistException;
import com.dbsnack.repositories.training.ParticipatedTrainingRepository;
import com.dbsnack.services.training.ParticipatedLessonService;
import com.dbsnack.services.training.ParticipatedTrainingService;
import com.dbsnack.services.training.TrainingService;
import com.dbsnack.services.user.StandardUserService;
import com.dbsnack.utils.LessonUtils;
import com.dbsnack.utils.ParticipatedTrainingUtils;
import com.dbsnack.utils.TopicUtils;
import com.dbsnack.utils.TrainingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;

@Service
@Transactional
public class ParticipatedTrainingServiceImpl implements ParticipatedTrainingService {

    private final ParticipatedTrainingRepository participatedTrainingRepository;

    private final ParticipatedLessonService participatedLessonService;

    private final TrainingService trainingService;

    private final StandardUserService standardUserService;

    private final ModelMapper modelMapper;

    @Autowired
    public ParticipatedTrainingServiceImpl(ParticipatedTrainingRepository participatedTrainingRepository,
                                           ParticipatedLessonService participatedLessonService,
                                           TrainingService trainingService,
                                           StandardUserService standardUserService,
                                           ModelMapper modelMapper) {
        this.participatedTrainingRepository = participatedTrainingRepository;
        this.participatedLessonService = participatedLessonService;
        this.trainingService = trainingService;
        this.standardUserService = standardUserService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ParticipatedTrainingLearnModel learn(long trainingId, String username) {
        ParticipatedTraining participatedTraining = this.participatedTrainingRepository.findOneByParticipantUsernameAndTrainingId(username, trainingId);
        if (participatedTraining == null) {
            participatedTraining = this.createParticipatedTraining(trainingId, username);
        }

        return this.modelMapper.map(participatedTraining, ParticipatedTrainingLearnModel.class);
    }

    @Override
    public ParticipatedTrainingLearnModel getNextLesson(long participatedTrainingId) {
        ParticipatedTraining participatedTraining = this.participatedTrainingRepository.findOne(participatedTrainingId);
        if (participatedTraining == null) {
            throw new ParticipatedTrainingDoesNotExistException(ParticipatedTrainingUtils.PARTICIPATED_TRAINING_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }

        ParticipatedLesson nextParticipatedLesson = participatedTraining.goToNextParticipatedLesson();
        if (nextParticipatedLesson.getId() == 0) {
            nextParticipatedLesson.setParticipatedTraining(participatedTraining);
            this.participatedLessonService.save(nextParticipatedLesson);
        }

        if (participatedTraining.getCurrentParticipatedLesson().getId() != nextParticipatedLesson.getId()) {
            participatedTraining.setCurrentParticipatedLesson(nextParticipatedLesson);
        }

        return this.modelMapper.map(participatedTraining, ParticipatedTrainingLearnModel.class);
    }

    @Override
    public ParticipatedTrainingLearnModel getPreviousLesson(long participatedTrainingId) {
        ParticipatedTraining participatedTraining = this.participatedTrainingRepository.findOne(participatedTrainingId);
        if (participatedTraining == null) {
            throw new ParticipatedTrainingDoesNotExistException(ParticipatedTrainingUtils.PARTICIPATED_TRAINING_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }

        participatedTraining.goToPreviousParticipatedLesson();
        return this.modelMapper.map(participatedTraining, ParticipatedTrainingLearnModel.class);
    }

    private ParticipatedTraining createParticipatedTraining(long trainingId, String username) {
        Training training = this.trainingService.getTrainingById(trainingId);
        if (training == null) {
            throw new TrainingDoesNotExistException(TrainingUtils.TRAINING_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }

        User user = this.standardUserService.getUserByUsername(username);
        ParticipatedTraining participatedTraining = new ParticipatedTraining();
        participatedTraining.setTraining(training);
        participatedTraining.setParticipant(user);

        Topic firstTopic = training.getTopics()
                .stream()
                .sorted(Comparator.comparingLong(Topic::getSequence))
                .findFirst()
                .orElse(null);
        if (firstTopic == null) {
            throw new TopicsNotFoundException(TopicUtils.TOPICS_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        Lesson firstLesson = firstTopic.getLessons()
                .stream()
                .sorted(Comparator.comparingLong(Lesson::getSequence))
                .findFirst()
                .orElse(null);

        if (firstLesson == null) {
            throw new LessonsNotFoundException(LessonUtils.LESSONS_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        participatedTraining.addParticipatedLesson(new ParticipatedLesson(firstLesson));

        this.participatedTrainingRepository.save(participatedTraining);

        return participatedTraining;
    }
}
