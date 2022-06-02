package banking.repository;

import banking.entity.Account;
import banking.entity.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

import static banking.Main.CONNECTION;
import static banking.Main.TABLE_NAME;

public class SQLite {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  %s (" +
            "id INTEGER, " +
            "number VARCHAR(16), " +
            "pin VARCHAR(4), " +
            "balance INTEGER DEFAULT 0)";

    private static final String INSERT_INTO = "INSERT INTO %s (id, number, pin, balance) " +
            "VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL = "SELECT * FROM %s WHERE number=%s AND pin=%s";
    private static final String ADD_INCOME = "UPDATE %s SET balance=%d WHERE number=%s";
    private static final String DELETE_ACCOUNT = "DELETE FROM %s WHERE number=%s";
    private static final String IS_EXISTS = "SELECT * FROM %s WHERE number=%s";


    public static Connection createConnection(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName;
        SQLiteDataSource source = new SQLiteDataSource();
        source.setUrl(url);
        return source.getConnection();
    }

    public static void createTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(String.format(CREATE_TABLE, TABLE_NAME));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveAccount(int id, String cardNum, String pin, int balance) {
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

    public static Account loadAccount(String num, String pinA) {
        try (Statement statement = CONNECTION.createStatement()) {
            try (ResultSet result = statement.executeQuery(String.format(SELECT_ALL, TABLE_NAME, num, pinA))) {
                int aId = result.getInt("id");
                String cardNum = result.getString("number");
                String pin = result.getString("pin");
                int balance = result.getInt("balance");

                Card card = new Card(cardNum, pin);
                return new Account(card, balance);
            }

        } catch (SQLException e) {
            return null;
        }
    }

    public static void addIncome(int amount, String cardNum) {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(String.format(ADD_INCOME, TABLE_NAME, amount, cardNum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAccount(String cardNum) {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(String.format(DELETE_ACCOUNT, TABLE_NAME, cardNum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Account isExists(String cardNum) {
        try(Statement statement = CONNECTION.createStatement()) {
            try(ResultSet set = statement.executeQuery(String.format(IS_EXISTS, TABLE_NAME, cardNum))) {
                int id = set.getInt("id");
                String number = set.getString("number");
                String pin = set.getString("pin");
                int balance = set.getInt("balance");
                Card card = new Card(number, pin);
                return new Account(card, balance);
            }
        } catch (SQLException e) {
            return null;
        }
    }



}
