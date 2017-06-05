package com.dbsnack.repositories.training;

import com.dbsnack.domain.entities.trainings.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
