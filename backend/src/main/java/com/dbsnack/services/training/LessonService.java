package com.dbsnack.services.training;

import com.dbsnack.domain.entities.trainings.Lesson;
import com.dbsnack.domain.models.bandingModels.LessonEditorModel;

public interface LessonService {

    void update(LessonEditorModel lessonEditorModel, long lessonId);

    Lesson getOneById(long id);
}
