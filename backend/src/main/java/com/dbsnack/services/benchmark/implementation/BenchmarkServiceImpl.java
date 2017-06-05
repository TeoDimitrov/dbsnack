package com.dbsnack.services.benchmark.implementation;

import com.dbsnack.domain.entities.trainings.ParticipatedLesson;
import com.dbsnack.domain.models.viewModels.RelationalDatabaseResult;
import com.dbsnack.services.benchmark.BenchmarkService;
import com.dbsnack.services.training.ParticipatedLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.StringJoiner;

@Service
@Transactional
public class BenchmarkServiceImpl implements BenchmarkService {

    private final ParticipatedLessonService participatedLessonService;

    @Autowired
    public BenchmarkServiceImpl(ParticipatedLessonService participatedLessonService) {
        this.participatedLessonService = participatedLessonService;
    }

    @Override
    public boolean benchmark(RelationalDatabaseResult relationalDatabaseResult, long participatedLessonId) {
        if(relationalDatabaseResult.getQueryResult() == null){
            return false;
        }

        ParticipatedLesson participatedLesson = this.participatedLessonService.findOneById(participatedLessonId);
        String expectedResult = participatedLesson.getLesson().getAnswer();
        StringBuilder actualResult = new StringBuilder();
        List<String[]> rows = relationalDatabaseResult.getQueryResult().getRowData();
        for (String[] row : rows) {
            actualResult.append(String.join(",", row));
            actualResult.append("\n");
        }

        boolean isMatch = false;
        if (actualResult.toString().trim().equals(expectedResult.trim())){
            isMatch = true;
        }

        return isMatch;
    }
}
