package com.dbsnack.services.training;

import com.dbsnack.domain.entities.trainings.Topic;
import com.dbsnack.domain.models.bandingModels.LessonEditorModel;
import com.dbsnack.domain.models.bandingModels.TopicEditorModel;

public interface TopicService {

    void update(TopicEditorModel topicEditorModel, long topicId);

    void addLesson(LessonEditorModel lessonEditorModel, long topicId);

    Topic findOneById(long id);
}
