package com.dbsnack.services.training.implementation;

import com.dbsnack.domain.entities.trainings.Lesson;
import com.dbsnack.domain.entities.trainings.Topic;
import com.dbsnack.domain.models.bandingModels.LessonEditorModel;
import com.dbsnack.domain.models.bandingModels.TopicEditorModel;
import com.dbsnack.repositories.training.TopicRepository;
import com.dbsnack.services.training.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void update(TopicEditorModel topicEditorModel, long topicId) {
        Topic topic = this.topicRepository.findOne(topicId);
        topic.setName(topicEditorModel.getName());
        topic.setDescription(topicEditorModel.getDescription());
        topic.setActive(topicEditorModel.isActive());
        topic.setSequence(topicEditorModel.getSequence());
    }

    @Override
    public void addLesson(LessonEditorModel lessonEditorModel, long topicId) {
        Topic topic = this.topicRepository.findOne(topicId);
        Lesson lesson = this.modelMapper.map(lessonEditorModel, Lesson.class);
        lesson.setCreatedOn(new Date());
        lesson.setTopic(topic);
        topic.addLesson(lesson);
    }

    @Override
    public Topic findOneById(long id) {
        return this.topicRepository.findOne(id);
    }
}
