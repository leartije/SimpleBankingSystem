package banking;

import banking.repository.SQLite;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static final String TABLE_NAME = "card";
    private static final String UNKNOWN = "unknown";
    private static final String NO_DB_NAME = "Can't retrieve database name";
    private static final String FILE_NAME = "-fileName";
    public static Connection CONNECTION;

    public static void main(String[] args) throws SQLException {

        String dbName = getDbName(args);
        if (dbName.equals(UNKNOWN)) {
            System.out.println(NO_DB_NAME);
            return;
        }
        CONNECTION = SQLite.createConnection(dbName);
        new Program().start();
    }

    public static String getDbName(String[] args) {
        if (args.length > 1 && args[0].equals(FILE_NAME)) {
            return args[1];
        }
        return UNKNOWN;
    }

}