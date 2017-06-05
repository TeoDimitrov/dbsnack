package com.dbsnack.repositories.training;

import com.dbsnack.domain.entities.trainings.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {
}
