package com.dbsnack.controllers.learn;

import com.dbsnack.domain.models.viewModels.RelationalDatabaseResult;

public interface BenchmarkController {

    RelationalDatabaseResult checkAnswer(long participatedLessonId, String sqlQuery);
}
