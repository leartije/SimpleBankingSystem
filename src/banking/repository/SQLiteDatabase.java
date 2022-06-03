package banking.repository;

import banking.entity.Account;
import banking.entity.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

import static banking.Main.DB_NAME;
import static banking.Main.TABLE_NAME;
import static banking.constants.Queries.*;

public class SQLiteDatabase {

    private final Connection CONNECTION;

    public SQLiteDatabase() throws SQLException {
        this.CONNECTION = createConnection(DB_NAME);
    }

    public Connection createConnection(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName;
        SQLiteDataSource source = new SQLiteDataSource();
        source.setUrl(url);
        return source.getConnection();
    }

    public void closeConnection() {
        try {
            CONNECTION.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(String.format(CREATE_TABLE, TABLE_NAME));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAccount(int id, String cardNum, String pin, int balance) {
        try (PreparedStatement statement = CONNECTION.prepareStatement(String.format(INSERT_INTO, TABLE_NAME))) {

            statement.setInt(1, id);
            statement.setString(2, cardNum);
            statement.setString(3, pin);
            statement.setInt(4, balance);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account loadAccount(String num, String pin) {
        try (Statement statement = CONNECTION.createStatement()) {
            try (ResultSet result = statement.executeQuery(String.format(SELECT_ALL, TABLE_NAME, num, pin))) {
                String cardNum = result.getString(NUMBER);
                String cardPin = result.getString(PIN);
                int cardBalance = result.getInt(BALANCE);

                Card card = new Card(cardNum, cardPin);
                return new Account(card, cardBalance);
            }

        } catch (SQLException e) {
            return null;
        }
    }

    public void changeAccountBalance(int amount, String cardNum) {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(String.format(ADD_INCOME, TABLE_NAME, amount, cardNum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAccount(String cardNum) {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(String.format(DELETE_ACCOUNT, TABLE_NAME, cardNum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account ifExists(String cardNum) {
        try (Statement statement = CONNECTION.createStatement()) {
            try (ResultSet set = statement.executeQuery(String.format(IF_EXISTS, TABLE_NAME, cardNum))) {
                String number = set.getString(NUMBER);
                String pin = set.getString(PIN);
                int balance = set.getInt(BALANCE);
                Card card = new Card(number, pin);
                return new Account(card, balance);
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public int getLastId() {
        try(Statement statement = CONNECTION.createStatement()) {
            try(ResultSet set = statement.executeQuery(String.format(LAST_ID, TABLE_NAME, TABLE_NAME))) {
                return set.getInt(ID);
            }
        } catch (SQLException e) {
            return -1;
        }
    }
}
