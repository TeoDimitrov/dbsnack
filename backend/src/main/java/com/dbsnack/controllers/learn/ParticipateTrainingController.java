package com.dbsnack.controllers.learn;


import com.dbsnack.domain.models.viewModels.ParticipatedTrainingLearnModel;
import com.dbsnack.exception.training.LessonsNotFoundException;
import com.dbsnack.exception.training.ParticipatedTrainingDoesNotExistException;
import com.dbsnack.exception.training.TopicsNotFoundException;
import com.dbsnack.exception.training.TrainingDoesNotExistException;
import com.dbsnack.services.training.ParticipatedTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/participated/training/")
public class ParticipateTrainingController {

    private final ParticipatedTrainingService participatedTrainingService;

    @Autowired
    public ParticipateTrainingController(ParticipatedTrainingService participatedTrainingService) {
        this.participatedTrainingService = participatedTrainingService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("start/training/{trainingId}")
    ParticipatedTrainingLearnModel getTrainingToLearn(@PathVariable long trainingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return this.participatedTrainingService.learn(trainingId, username);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{participatedTrainingId}/next/lesson")
    ParticipatedTrainingLearnModel getNextLesson(@PathVariable long participatedTrainingId) {
        return this.participatedTrainingService.getNextLesson(participatedTrainingId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{participatedTrainingId}/previous/lesson")
    ParticipatedTrainingLearnModel getPreviousLesson(@PathVariable long participatedTrainingId) {
        return this.participatedTrainingService.getPreviousLesson(participatedTrainingId);
    }

    @ExceptionHandler(TrainingDoesNotExistException.class)
    public ResponseEntity handleTrainingDoesNotExists(TrainingDoesNotExistException e) {
        return new ResponseEntity<>(Collections.singletonMap("TrainingException",
                e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParticipatedTrainingDoesNotExistException.class)
    public ResponseEntity handleParticipatedTrainingDoesNotExists(ParticipatedTrainingDoesNotExistException e) {
        return new ResponseEntity<>(Collections.singletonMap("ParticipatedTrainingException",
                e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TopicsNotFoundException.class)
    public ResponseEntity handleTopicsNotFound(TopicsNotFoundException e) {
        return new ResponseEntity<>(Collections.singletonMap("TopicException",
                e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LessonsNotFoundException.class)
    public ResponseEntity handleLessonsNotFound(LessonsNotFoundException e) {
        return new ResponseEntity<>(Collections.singletonMap("LessonException",
                e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
