package com.dbsnack.services.training.implementation;

import com.dbsnack.domain.entities.trainings.Lesson;
import com.dbsnack.domain.models.bandingModels.LessonEditorModel;
import com.dbsnack.repositories.training.LessonRepository;
import com.dbsnack.services.training.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void update(LessonEditorModel lessonEditorModel, long lessonId) {
        Lesson lesson = this.lessonRepository.findOne(lessonId);
        lesson.setName(lessonEditorModel.getName());
        lesson.setDescription(lessonEditorModel.getDescription());
        lesson.setActive(lessonEditorModel.isActive());
        lesson.setAnswer(lessonEditorModel.getAnswer());
        lesson.setContent(lessonEditorModel.getContent());
        lesson.setAnswerVerificationQuery(lessonEditorModel.getAnswerVerificationQuery());
        lesson.setExercise(lessonEditorModel.getExercise());
        lesson.setSequence(lessonEditorModel.getSequence());
    }

    @Override
    public Lesson getOneById(long id) {
        return this.lessonRepository.findOne(id);
    }
}
