package com.dbsnack.repositories.training;

import com.dbsnack.domain.entities.trainings.ParticipatedLesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipatedLessonRepository extends CrudRepository<ParticipatedLesson, Long> {
}
