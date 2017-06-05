package com.dbsnack.domain.models.viewModels;

import java.util.List;
import java.util.Map;

public class RelationalDatabaseResult {

    private QueryResult queryResult;

    private Map<String, List<ColumnMetaData>> tablesMetaData;

    private String message;

    private boolean isCorrect;

    public QueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

    public Map<String, List<ColumnMetaData>> getTablesMetaData() {
        return tablesMetaData;
    }

    public void setTablesMetaData(Map<String, List<ColumnMetaData>> tablesMetaData) {
        this.tablesMetaData = tablesMetaData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
