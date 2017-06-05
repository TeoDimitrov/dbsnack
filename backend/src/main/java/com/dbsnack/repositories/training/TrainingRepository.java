package com.dbsnack.repositories.training;

import com.dbsnack.domain.entities.trainings.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query(value = "SELECT t FROM Training AS t " +
            "WHERE t.isActive = TRUE " +
            "ORDER BY t.technologyStack ASC, t.level ASC")
    List<Training> findAllActiveTrainings();

    @Query(value = "SELECT tr FROM Training AS tr " +
            "INNER JOIN tr.topics AS to " +
            "INNER JOIN to.lessons AS le " +
            "WHERE tr.id= :id " +
            "AND tr.isActive = TRUE " +
            "AND to.isActive = TRUE " +
            "AND le.isActive = TRUE " +
            "ORDER BY tr.id ASC, to.sequence ASC, le.sequence ASC")
    Training findActiveTrainingWithActiveTopicsWithActiveLessonsById(@Param("id") long id);

    @Query(value = "SELECT tr FROM Training AS tr " +
            "WHERE tr.author.username = :username ")
    List<Training> findAllByAuthor(@Param("username") String username);

    List<Training> findAll();
}
