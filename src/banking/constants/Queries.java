package banking.constants;

public class Queries {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  %s (" +
            "id INTEGER, " +
            "number VARCHAR(16), " +
            "pin VARCHAR(4), " +
            "balance INTEGER DEFAULT 0);";

    public static final String INSERT_INTO = "INSERT INTO %s (id, number, pin, balance) " +
            "VALUES (?, ?, ?, ?);";

    public static final String SELECT_ALL = "SELECT * FROM %s WHERE number=\"%s\" AND pin=\"%s\";";
    public static final String ADD_INCOME = "UPDATE %s SET balance=\"%d\" WHERE number=\"%s\";";
    public static final String DELETE_ACCOUNT = "DELETE FROM %s WHERE number=\"%s\";";
    public static final String IF_EXISTS = "SELECT * FROM %s WHERE number=\"%s\";";
    public static final String LAST_ID = "SELECT id FROM %s WHERE id=(SELECT max(id) FROM %s);";

    //fields
    public static final String ID = "id";
    public static final String NUMBER = "number";
    public static final String PIN = "pin";
    public static final String BALANCE = "balance";

}
