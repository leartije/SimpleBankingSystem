package banking.constants;

public class Text {

    public static final String MENU = "1. Create an account\n" +
            "2. Log into account\n" +
            "0. Exit";

    public static final String LOG_IN_MENU = "1. Balance\n" +
            "2. Add income\n" +
            "3. Do transfer\n" +
            "4. Close account\n" +
            "5. Log out\n" +
            "0. Exit";

    public static final String CARD_HAS_BEEN_CREATED = "Your card has been created\n" +
            "Your card number:\n" +
            "%s\n" +
            "Your card PIN:\n" +
            "%s\n";

    public static final String ENTER_YOUR_CARD_NUMBER = "Enter your card number:";
    public static final String ENTER_YOUR_PIN = "Enter your PIN:";
    public static final String WRONG_LOGIN_INF = "Wrong card number or PIN!";
    public static final String SUCCESSFULLY_LOGGED_IN = "You have successfully logged in!";
    public static final String SUCCESSFULLY_LOGGED_OUT = "You have successfully logged out!";
    public static final String BYE = "Bye!";
    public static final String DEFAULT_MSG = "Wrong input, try 1 trough 5 or 0";
    public static final String DEFAULT_MSG_MAIN = "Wrong input, try 1, 2 or 0";
    public static final String BALANCE = "Balance: %d%n";
    public static final String ENTER_INCOME = "Enter income:";
    public static final String INCOME_ADDED = "Income was added!";

    //transfer
    public static final String TRANSFER = "Transfer";
    public static final String ENTER_CARD_NUMBER = "Enter card number:";
    public static final String SAME_ACC_ERROR = "You can't transfer money to the same account!";
    public static final String NO_LUHN_NUM_ERROR = "Probably you made a mistake in the card number. Please try again!";
    public static final String CARD_DONT_EXIST_ERROR = "Such a card does not exist.";
    public static final String NOT_ENOUGH_MONEY_ERROR = "Not enough money!";
    public static final String ENTER_AMOUNT = "Enter how much money you want to transfer:";
    public static final String SUCCESS = "Success!";

    //close acc
    public static final String CLOSE_ACCOUNT = "The account has been closed!";


}
