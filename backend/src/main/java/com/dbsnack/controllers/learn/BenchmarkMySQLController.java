package com.dbsnack.controllers.learn;

import com.dbsnack.domain.models.viewModels.RelationalDatabaseResult;
import com.dbsnack.exception.database.DatabaseInitializationException;
import com.dbsnack.exception.database.DatabaseTerminationException;
import com.dbsnack.exception.database.IncorrectSqlQuery;
import com.dbsnack.services.benchmark.BenchmarkService;
import com.dbsnack.services.database.RdbmsDatabaseService;
import com.dbsnack.services.training.ParticipatedLessonService;
import com.dbsnack.utils.DatabaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/benchmark/mysql/")
public class BenchmarkMySQLController implements BenchmarkController {

    @Qualifier("MySQL")
    private final RdbmsDatabaseService rdbmsDatabaseService;

    private final BenchmarkService benchmarkService;

    private final ParticipatedLessonService participatedLessonService;

    @Autowired
    public BenchmarkMySQLController(RdbmsDatabaseService rdbmsDatabaseService,
                                    BenchmarkService benchmarkService,
                                    ParticipatedLessonService participatedLessonService) {
        this.rdbmsDatabaseService = rdbmsDatabaseService;
        this.benchmarkService = benchmarkService;
        this.participatedLessonService = participatedLessonService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("participated/lesson/{participatedLessonId}")
    public RelationalDatabaseResult checkAnswer(@PathVariable long participatedLessonId,
                                                @RequestBody String sqlQuery) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RelationalDatabaseResult relationalDatabaseResult = this.rdbmsDatabaseService.getStatementResult(sqlQuery, username);
        this.participatedLessonService.setSubmission(participatedLessonId, sqlQuery);
        if (this.benchmarkService.benchmark(relationalDatabaseResult, participatedLessonId)) {
            this.participatedLessonService.complete(participatedLessonId);
            relationalDatabaseResult.setCorrect(true);
            relationalDatabaseResult.setMessage(DatabaseUtils.CORRECT_QUERY_EXCEPTION_MESSAGE);
        } else {
            relationalDatabaseResult.setCorrect(false);
            relationalDatabaseResult.setMessage(DatabaseUtils.INCORRECT_QUERY_EXCEPTION_MESSAGE);
        }

        return relationalDatabaseResult;
    }

    @ExceptionHandler(DatabaseTerminationException.class)
    public ResponseEntity handleDatabaseTerminationException(DatabaseTerminationException e) {
        return new ResponseEntity<>(Collections.singletonMap("DatabaseTerminationException",
                e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseInitializationException.class)
    public ResponseEntity handleDatabaseInitializationException(DatabaseInitializationException e) {
        return new ResponseEntity<>(Collections.singletonMap("DatabaseInitializationException",
                e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectSqlQuery.class)
    public ResponseEntity handleIncorrectSqlQuery(IncorrectSqlQuery e) {
        return new ResponseEntity<>(Collections.singletonMap("queryResult",
                e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }
}
