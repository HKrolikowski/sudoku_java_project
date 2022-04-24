package pl.first.sudoku;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pl.first.sudoku.exceptions.DaoException;
import pl.first.sudoku.exceptions.JdbcClosingConnectionException;
import pl.first.sudoku.exceptions.JdbcConnectingToBaseException;
import pl.first.sudoku.exceptions.JdbcExistingBoardException;
import pl.first.sudoku.exceptions.JdbcExistingTableException;
import pl.first.sudoku.exceptions.JdbcQueryException;
import pl.first.sudoku.exceptions.JdbcReadException;
import pl.first.sudoku.exceptions.JdbcWriteException;



public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private String filename;
    private String jdbcUrl;

    public JdbcSudokuBoardDao(String filename) {
        this.filename = filename;
        this.jdbcUrl = "jdbc:sqlite:database.db";
    }

    private Connection connectingToBase(String jdbcurl) throws DaoException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(jdbcurl);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new JdbcConnectingToBaseException(e.getLocalizedMessage(), e);
        }
    }

    private boolean ifTableExists(Connection connection, String name) throws DaoException {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, name, null);
            return resultSet.next();
        } catch (SQLException e) {
            throw new JdbcExistingTableException(e.getLocalizedMessage(), e);
        }
    }

    private boolean ifBoardExists(Connection connection) throws DaoException {
        String selectedData = "select boardName from SudokuBoardTable where boardName=?";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(selectedData)) {
            preparedStatement.setString(1, filename);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new JdbcClosingConnectionException(e.getLocalizedMessage(), e);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new JdbcExistingBoardException(e.getLocalizedMessage(), e);
        }
    }

    private void createSudokuBoardTable(Connection connection) throws DaoException {
        String createTable = "create table SudokuBoardTable(" + "id integer primary key,"
                + "boardName varchar(20))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcQueryException(e.getLocalizedMessage(), e);
        }
    }

    private void createSudokuFieldTable(Connection connection) throws DaoException {
        String createTable = "create table SudokuFieldTable("
                + "position integer check (position between 1 and 81),"
                + "value integer check (value between 0 and 9),"
                + "id_SudokuBoard int,"
                + "foreign KEY (id_SudokuBoard) REFERENCES SudokuBoardTable(id))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcQueryException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public SudokuBoard read() throws DaoException {
        SudokuBoard sudokuBoard = new SudokuBoard();
        Connection connection = this.connectingToBase(jdbcUrl);
        String selectedData = "select position, value from SudokuFieldTable"
                + " join SudokuBoardTable on SudokuBoardTable.id = SudokuFieldTable.id_SudokuBoard "
                + "where SudokuBoardTable.boardName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectedData)) {
            preparedStatement.setString(1, filename);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int position = resultSet.getInt(1);
                sudokuBoard.set((position - 1) % 9, (position - 1) / 9, resultSet.getInt(2));
            }
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcReadException(e.getLocalizedMessage(), e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new JdbcClosingConnectionException(e.getLocalizedMessage(), e);
        }

        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        Connection connection = this.connectingToBase("jdbc:sqlite:database.db");
        if (!this.ifTableExists(connection, "SudokuBoardTable")) {
            this.createSudokuBoardTable(connection);
        }
        if (!this.ifTableExists(connection, "SudokuFieldTable")) {
            this.createSudokuFieldTable(connection);
        }
        if (this.ifBoardExists(connection)) {
            return;
        }
        String insertData =
                "insert into SudokuBoardTable("
                        + "boardName) values (?)";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(insertData)) {
            preparedStatement.setString(1, filename);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcWriteException(e.getLocalizedMessage(), e);
        }
        String selectedData = "select id from " + "SudokuBoardTable" + " where boardName=?";
        int id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectedData)) {
            preparedStatement.setString(1, filename);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcWriteException(e.getLocalizedMessage(), e);
        }
        insertData =
                "insert into SudokuFieldTable("
                        + "position, value, id_SudokuBoard) values (?,?,?)";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(insertData)) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    preparedStatement.setInt(1, x + 1 + y * 9);
                    preparedStatement.setInt(2, obj.get(x, y));
                    preparedStatement.setInt(3, id);
                    preparedStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcWriteException(e.getLocalizedMessage(), e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new JdbcClosingConnectionException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
