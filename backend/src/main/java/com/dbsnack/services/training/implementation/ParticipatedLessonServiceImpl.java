package com.dbsnack.services.training.implementation;

import com.dbsnack.domain.entities.trainings.ParticipatedLesson;
import com.dbsnack.repositories.training.ParticipatedLessonRepository;
import com.dbsnack.services.training.ParticipatedLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParticipatedLessonServiceImpl implements ParticipatedLessonService {

    private final ParticipatedLessonRepository participatedLessonRepository;

    @Autowired
    public ParticipatedLessonServiceImpl(ParticipatedLessonRepository participatedLessonRepository) {
        this.participatedLessonRepository = participatedLessonRepository;
    }

    @Override
    public void save(ParticipatedLesson participatedLesson) {
        this.participatedLessonRepository.save(participatedLesson);
    }

    @Override
    public ParticipatedLesson findOneById(long id) {
        return this.participatedLessonRepository.findOne(id);
    }

    @Override
    @Transactional
    public void setSubmission(long participatedTrainingId, String sqlStatement) {
        ParticipatedLesson participatedLesson = this.participatedLessonRepository.findOne(participatedTrainingId);
        participatedLesson.setLastSubmission(sqlStatement);
    }

    @Override
    @Transactional
    public void complete(long participatedTrainingId) {
        ParticipatedLesson participatedLesson = this.participatedLessonRepository.findOne(participatedTrainingId);
        participatedLesson.setCompleted(true);
    }
}
