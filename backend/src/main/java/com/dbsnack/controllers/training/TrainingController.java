package com.dbsnack.controllers.training;

import com.dbsnack.domain.models.viewModels.TrainingActiveModel;
import com.dbsnack.domain.models.viewModels.TrainingDetailsModel;
import com.dbsnack.exception.training.TrainingDoesNotExistException;
import com.dbsnack.services.training.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainingController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/trainings")
    public List<TrainingActiveModel> getActiveTrainings() {
        return this.trainingService.getAllActiveTrainings();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/trainings/{id}/details")
    public TrainingDetailsModel getTrainingDetails(@PathVariable long id) {
        return this.trainingService.getTrainingDetails(id);
    }

    @ExceptionHandler(TrainingDoesNotExistException.class)
    public ResponseEntity handleTrainingDoesNotExists(TrainingDoesNotExistException e) {
        return new ResponseEntity<>(Collections.singletonMap("TrainingException",
                e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
