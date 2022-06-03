package banking;

import java.sql.SQLException;

public class Main {

    public static final String TABLE_NAME = "card";
    private static final String UNKNOWN = "unknown";
    private static final String NO_DB_NAME = "Can't retrieve database name";
    private static final String FILE_NAME = "-fileName";
    public static String DB_NAME;

    public static void main(String[] args) throws SQLException {

        DB_NAME = getDbName(args);
        if (DB_NAME.equals(UNKNOWN)) {
            System.out.println(NO_DB_NAME);
            return;
        }
        new Program().start();
    }

    public static String getDbName(String[] args) {
        if (args.length > 1 && args[0].equals(FILE_NAME)) {
            return args[1];
        }
        return UNKNOWN;
    }

}