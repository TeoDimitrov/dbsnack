package com.dbsnack.repositories.training;

import com.dbsnack.domain.entities.trainings.ParticipatedTraining;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipatedTrainingRepository extends CrudRepository<ParticipatedTraining, Long> {

    ParticipatedTraining findOneByParticipantUsernameAndTrainingId(String username, long trainingId);
}
