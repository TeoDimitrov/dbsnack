package com.dbsnack.services.database;

import com.dbsnack.domain.models.viewModels.RelationalDatabaseResult;

import java.sql.ResultSet;

public interface RdbmsDatabaseService {

    RelationalDatabaseResult getStatementResult(String sqlStatement, String username);

    RelationalDatabaseResult getStatementResult(String sqlStatement, String verificationSqlStatement, String username);
}
