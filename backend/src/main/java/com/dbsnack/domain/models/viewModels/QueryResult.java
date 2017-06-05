package com.dbsnack.domain.models.viewModels;

import java.util.ArrayList;
import java.util.List;

public class QueryResult {

    List<String> columnNames;

    List<String[]> rowData;

    public QueryResult() {
        this.setColumnNames(new ArrayList<>());
        this.setRowData(new ArrayList<>());
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<String[]> getRowData() {
        return rowData;
    }

    public void setRowData(List<String[]> rowData) {
        this.rowData = rowData;
    }

    public void addColumnName(String columnName){
        this.getColumnNames().add(columnName);
    }

    public void addRow(String[] row){
        this.getRowData().add(row);
    }
}
