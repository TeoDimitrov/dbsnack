package com.dbsnack.controllers.editor;

import com.dbsnack.domain.models.bandingModels.LessonEditorModel;
import com.dbsnack.domain.models.bandingModels.TopicEditorModel;
import com.dbsnack.domain.models.bandingModels.TrainingEditorModel;
import com.dbsnack.services.training.LessonService;
import com.dbsnack.services.training.TopicService;
import com.dbsnack.services.training.TrainingService;
import com.dbsnack.utils.RoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editor")
public class EditorController {

    private final TrainingService trainingService;

    private final TopicService topicService;

    private final LessonService lessonService;

    @Autowired
    public EditorController(TrainingService trainingService, TopicService topicService, LessonService lessonService) {
        this.trainingService = trainingService;
        this.topicService = topicService;
        this.lessonService = lessonService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/trainings")
    List<TrainingEditorModel> getTrainingsForEdit() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(RoleUtils.ROLE_ADMIN)) {
            return this.trainingService.findAllTrainingsOnEditorPage();
        }

        return this.trainingService.findAllTrainingsByAuthorOnEditorPage(authentication.getName());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/trainings/add")
    void addTraining(@RequestBody TrainingEditorModel trainingEditorModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        this.trainingService.save(trainingEditorModel, username);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/trainings/update/{trainingId}")
    void updateTraining(@PathVariable long trainingId, @RequestBody TrainingEditorModel trainingEditorModel) {
        this.trainingService.update(trainingEditorModel, trainingId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/trainings/add/topic/{trainingId}")
    void addTopic(@PathVariable long trainingId, @RequestBody TopicEditorModel topicEditorModel) {
        this.trainingService.addTopic(topicEditorModel, trainingId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/topics/update/{topicId}")
    void updateTopic(@PathVariable long topicId, @RequestBody TopicEditorModel topicEditorModel) {
        this.topicService.update(topicEditorModel, topicId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/topics/add/lesson/{topicId}")
    void addLesson(@PathVariable long topicId, @RequestBody LessonEditorModel lessonEditorModel) {
        this.topicService.addLesson(lessonEditorModel, topicId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/lessons/update/{lessonId}")
    void updateLesson(@PathVariable long lessonId, @RequestBody LessonEditorModel lessonEditorModel) {
        this.lessonService.update(lessonEditorModel, lessonId);
    }
}
