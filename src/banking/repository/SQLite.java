package banking.repository;

import banking.entity.Account;
import banking.entity.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLite {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  %s (" +
            "id INTEGER, " +
            "number VARCHAR, " +
            "pin VARCHAR, " +
            "balance INTEGER DEFAULT 0)";

    private static final String INSERT_INTO = "INSERT INTO %s (id, number, pin, balance) " +
            "VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL = "SELECT * FROM %s WHERE number=%s AND pin=%s";


    public static Connection connection(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName;
        SQLiteDataSource source = new SQLiteDataSource();
        source.setUrl(url);
        return source.getConnection();
    }

    public static void createTable(Connection conn, String tableName) {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(String.format(CREATE_TABLE, tableName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveAccount(
            Connection conn,
            String tableName,
            int id,
            String cardNum,
            String pin,
            int balance
    ) {
        try (PreparedStatement statement = conn.prepareStatement(String.format(INSERT_INTO, tableName))) {

            statement.setInt(1, id);
            statement.setString(2, cardNum);
            statement.setString(3, pin);
            statement.setInt(4, balance);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Account loadAccount(
            Connection conn,
            String tableName,
            String num,
            String pinA
    ) {
        try (Statement statement = conn.createStatement()) {
            try (ResultSet result = statement.executeQuery(String.format(SELECT_ALL, tableName, num, pinA))) {
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


}
