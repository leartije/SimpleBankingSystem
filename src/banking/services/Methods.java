package banking.services;

import banking.entity.Account;
import banking.entity.Card;
import banking.repository.SQLite;

import java.sql.Connection;
import java.util.Scanner;

import static banking.constants.Text.*;

public class Methods {
    private final Scanner scanner = new Scanner(System.in);
    private final Generate generate;
    private final Connection connection;
    private final String tableName;

    public Methods(Connection connection, String tableName) {
        this.generate = new Generate();
        this.connection = connection;
        this.tableName = tableName;
    }

    public void generateCard() {
        String cardNum = generate.generateCardNum();
        String pin = generate.generatePinNum();
        Account account = new Account(new Card(cardNum, pin), 0);
        SQLite.saveAccount(connection, tableName,
                account.getAccountId(),
                account.getCard().getCardNum(),
                account.getCard().getPin(),
                account.getAmount());
        System.out.printf(CARD_HAS_BEEN_CREATED, cardNum, pin);
    }

    public Account login() {
        System.out.println(ENTER_YOUR_CARD_NUMBER);
        String cardNum = scanner.nextLine();
        System.out.println(ENTER_YOUR_PIN);
        String pin = scanner.nextLine();
        Account temp = SQLite.loadAccount(connection, tableName, cardNum, pin);
        if (temp != null) {
            return temp;
        }
        System.out.println(WRONG_LOGIN_INF);
        return null;
    }

}
