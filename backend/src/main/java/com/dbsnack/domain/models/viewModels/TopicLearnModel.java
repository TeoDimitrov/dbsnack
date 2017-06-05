package com.dbsnack.domain.models.viewModels;

public class TopicLearnModel {

    private String name;

    private TrainingLearnModel training;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingLearnModel getTraining() {
        return training;
    }

    public void setTraining(TrainingLearnModel training) {
        this.training = training;
    }
}
