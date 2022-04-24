package pl.comp.view.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pl.first.sudoku.exceptions.DbConnectionClosingException;
import pl.first.sudoku.exceptions.DbConnectionException;
import pl.first.sudoku.exceptions.DbExecuteQueryException;
import pl.first.sudoku.exceptions.DbTableExistsException;

public class DatabaseFunctions {
    private String jdbcUrl;

    public DatabaseFunctions() {
        this.jdbcUrl = "jdbc:sqlite:database.db";
    }

    private Connection connect(String jdbcurl) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(jdbcurl);
            connection.setAutoCommit(false);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
            throw new DbConnectionException(e.getLocalizedMessage(), e);
        }
    }

    private void createTable(Connection connection) throws SQLException {
        String createTable =
                "create table SudokuBoardTable("
                        + "id integer primary key,"
                        + "boardName varchar(20), "
                        + "field integer check (field between 0 and 9),"
                        + "isEditable integer check (isEditable between 0 and 1))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
            connection.commit();
        } catch (SQLException e) {
            throw new DbExecuteQueryException(e.getLocalizedMessage(), e);
        }
    }

    private boolean ifTableExists(Connection connection) throws SQLException {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null,
                    null, "SudokuBoardTable", null);
            return resultSet.next();
        } catch (SQLException e) {
            throw new DbTableExistsException(e.getLocalizedMessage(), e);
        }
    }

    public List<String> selectAll() throws SQLException {
        Connection connection = this.connect(jdbcUrl);
        if (!ifTableExists(connection)) {
            this.createTable(connection);
        }
        List<String> savedBoards = new ArrayList<>();
        String selectedData = "select distinct boardName from SudokuBoardTable";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(selectedData)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                savedBoards.add(resultSet.getString(1));
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DbExecuteQueryException(e.getLocalizedMessage(), e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DbConnectionClosingException(e.getLocalizedMessage(), e);
        }
        return Collections.unmodifiableList(savedBoards);
    }
}
