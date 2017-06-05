package com.dbsnack.services.database.implementation;

import com.dbsnack.domain.models.viewModels.ColumnMetaData;
import com.dbsnack.domain.models.viewModels.QueryResult;
import com.dbsnack.domain.models.viewModels.RelationalDatabaseResult;
import com.dbsnack.exception.database.DatabaseInitializationException;
import com.dbsnack.exception.database.DatabaseTerminationException;
import com.dbsnack.exception.database.IncorrectSqlQuery;
import com.dbsnack.services.database.RdbmsDatabaseService;
import com.dbsnack.utils.DatabaseUtils;
import com.dbsnack.utils.MySQLUtils;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("MySQL")
public class MySQLDatabaseServiceImpl implements RdbmsDatabaseService {

    private Connection adminConnection;

    private Connection userConnection;

    private String databaseUser;

    private String databaseName;

    @Override
    public RelationalDatabaseResult getStatementResult(String sqlStatement, String username) {
        this.preExecution(username);
        RelationalDatabaseResult result = new RelationalDatabaseResult();
        QueryResult queryResult = this.executeStatement(sqlStatement);
        result.setQueryResult(queryResult);
        Map<String, List<ColumnMetaData>> tablesMetaData = this.getMetaDataStatement();
        result.setTablesMetaData(tablesMetaData);
        this.postExecution();
        return result;
    }

    @Override
    public RelationalDatabaseResult getStatementResult(String sqlStatement, String verificationSqlStatement, String username) {
        //// TODO: 29/05/2017 Implement when DDL comes
        this.preExecution(username);
        this.executeStatement(sqlStatement);
        //The is the result to expect
        this.executeStatement(verificationSqlStatement);
        this.postExecution();
        throw new NotImplementedException();
    }

    private void preExecution(String username) {
        this.generateDatabaseSchemaName(username);
        this.establishAdminConnection();
        this.prepareDatabaseContent();
        this.prepareDatabaseCredentials();
        this.establishUserConnection();
    }

    private void postExecution() {
        this.dropDatabase();
        this.dropCredentials();
        this.terminateConnection();
    }

    private void establishAdminConnection() {
        try {
            this.adminConnection = DriverManager.getConnection(MySQLUtils.URL, MySQLUtils.USER, MySQLUtils.PASSWORD);
        } catch (SQLException e) {
            this.postExecution();
            throw new DatabaseInitializationException(DatabaseUtils.INITIALIZATION_EXCEPTION_MESSAGE);
        }
    }

    private void establishUserConnection() {
        try {
            this.userConnection = DriverManager.getConnection(MySQLUtils.URL, this.databaseUser, null);
        } catch (SQLException e) {
            this.postExecution();
            throw new DatabaseInitializationException(DatabaseUtils.INITIALIZATION_EXCEPTION_MESSAGE);
        }
    }

    private void prepareDatabaseContent() {
        try {
            this.adminConnection.prepareStatement(MySQLUtils.getCreateDatabaseStatement(this.databaseName)).execute();
            this.adminConnection.prepareStatement(MySQLUtils.getSelectDatabaseStatement(this.databaseName)).execute();
            this.adminConnection.prepareStatement(MySQLUtils.getCreateTablesStatement()).execute();
        } catch (SQLException e) {
            this.postExecution();
            throw new DatabaseInitializationException(DatabaseUtils.INITIALIZATION_EXCEPTION_MESSAGE);
        }
    }

    private void prepareDatabaseCredentials() {
        try {
            this.adminConnection.prepareStatement(MySQLUtils.getCreateUserStatement(this.databaseUser)).execute();
            this.adminConnection.prepareStatement(MySQLUtils.getGrantUserPrivilegesStatement(this.databaseUser, this.databaseName)).execute();
        } catch (SQLException e) {
            this.postExecution();
            throw new DatabaseInitializationException(DatabaseUtils.INITIALIZATION_EXCEPTION_MESSAGE);
        }
    }

    private QueryResult executeStatement(String sqlStatement) {
        QueryResult queryResult;
        try {
            this.userConnection.createStatement().executeQuery(MySQLUtils.getSelectDatabaseStatement(this.databaseName));
            Statement statement = this.userConnection.createStatement();
            statement.setQueryTimeout(DatabaseUtils.QUERY_TIMEOUT_SECONDS);
            ResultSet resultSet;
            try {
                resultSet = statement.executeQuery(sqlStatement);
            } catch (SQLException ignore) {
                statement.execute(sqlStatement);
                return null;
            }

            queryResult = new QueryResult();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSetMetaData.getColumnLabel(i);
                queryResult.addColumnName(columnName);
            }

            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    String cellValue = resultSet.getString(i);
                    row[i - 1] = cellValue;
                }

                queryResult.addRow(row);
            }
        } catch (SQLException e) {
            this.postExecution();
            throw new IncorrectSqlQuery(e.getMessage());
        }

        return queryResult;
    }

    private Map<String, List<ColumnMetaData>> getMetaDataStatement() {
        Map<String, List<ColumnMetaData>> tablesMetaData;
        try {
            this.userConnection.createStatement().executeQuery(MySQLUtils.getSelectDatabaseStatement(this.databaseName));
            Statement statement = this.userConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(MySQLUtils.getSchemaMetaDataStatement());
            tablesMetaData = new HashMap<>();
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                String columnName = resultSet.getString(2);
                String dataType = resultSet.getString(3);
                if (tablesMetaData.containsKey(tableName)) {
                    tablesMetaData.get(tableName).add(new ColumnMetaData(columnName, dataType));
                } else {
                    List<ColumnMetaData> columnMetaDataList = new ArrayList<>();
                    columnMetaDataList.add(new ColumnMetaData(columnName, dataType));
                    tablesMetaData.put(tableName, columnMetaDataList);
                }
            }
        } catch (SQLException e) {
            this.postExecution();
            throw new DatabaseTerminationException(DatabaseUtils.TERMINATION_EXCEPTION_MESSAGE);
        }

        return tablesMetaData;
    }

    private void dropDatabase() {
        try {
            this.adminConnection.prepareStatement(MySQLUtils.getDropDatabaseStatement(this.databaseName)).execute();
        } catch (SQLException e) {
            this.dropCredentials();
            this.terminateConnection();
            throw new DatabaseTerminationException(DatabaseUtils.TERMINATION_EXCEPTION_MESSAGE);
        }
    }

    private void dropCredentials() {
        try {
            this.adminConnection.prepareStatement(MySQLUtils.getDropCredentialsStatement(this.databaseUser)).execute();
        } catch (SQLException e) {
            this.terminateConnection();
            throw new DatabaseTerminationException(DatabaseUtils.TERMINATION_EXCEPTION_MESSAGE);
        }
    }

    private void terminateConnection() {
        if (this.adminConnection != null) {
            try {
                this.adminConnection.close();
            } catch (SQLException e) {
                throw new DatabaseTerminationException(DatabaseUtils.TERMINATION_EXCEPTION_MESSAGE);
            }
        }

        if (this.userConnection != null) {
            try {
                this.userConnection.close();
            } catch (SQLException e) {
                throw new DatabaseTerminationException(DatabaseUtils.TERMINATION_EXCEPTION_MESSAGE);
            }
        }
    }

    private void generateDatabaseSchemaName(String username) {
        final MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(username.getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            this.databaseUser = new String(Hex.encode(resultByte));
            this.databaseName = this.databaseUser;
        } catch (NoSuchAlgorithmException e) {
            throw new DatabaseInitializationException(DatabaseUtils.INITIALIZATION_EXCEPTION_MESSAGE);
        }
    }
}
