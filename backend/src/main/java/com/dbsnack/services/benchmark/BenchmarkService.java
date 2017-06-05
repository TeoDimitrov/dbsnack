package com.dbsnack.services.benchmark;

import com.dbsnack.domain.models.viewModels.RelationalDatabaseResult;

import java.sql.ResultSet;

public interface BenchmarkService {

    boolean benchmark(RelationalDatabaseResult relationalDatabaseResult, long participatedLessonId);
}
