package banking.services;

import banking.entity.Account;
import banking.entity.Card;
import banking.repository.SQLite;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

import static banking.constants.Text.*;

public class AccountService {
    private final Scanner scanner = new Scanner(System.in);
    private final Generate generate;
    private final Connection connection;

    public AccountService(Connection connection) {
        this.generate = new Generate();
        this.connection = connection;
    }

    public void generateCard() {
        String cardNum = generate.generateCardNum();
        String pin = generate.generatePinNum();
        Account account = new Account(new Card(cardNum, pin), 0);
        SQLite.saveAccount(
                account.getAccountId(),
                account.getCard().getCardNum(),
                account.getCard().getPin(),
                account.getBalance());
        System.out.printf(CARD_HAS_BEEN_CREATED, cardNum, pin);
    }

    public Account login() {
        System.out.println(ENTER_YOUR_CARD_NUMBER);
        String cardNum = scanner.nextLine();
        System.out.println(ENTER_YOUR_PIN);
        String pin = scanner.nextLine();
        Account search = SQLite.loadAccount(cardNum, pin);
        if (search != null) {
            return search;
        }
        System.out.println(WRONG_LOGIN_INF);
        return null;
    }

    public void getBalance(int balance) {
        System.out.printf(BALANCE, balance);
    }

    public void addIncome(Account account) {
        int income;
        try {
            System.out.println(ENTER_INCOME);
            income = Integer.parseInt(scanner.nextLine());
            account.setBalance(income);
            SQLite.addIncome(account.getBalance(), account.getCard().getCardNum());
            System.out.println(INCOME_ADDED);
        } catch (NumberFormatException e) {
            System.out.println("Not valid input");
        }
    }

    public void transfer(Account account) {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNum = scanner.nextLine();
        if (!isLuhn(cardNum)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }
        Account focusAccount = SQLite.isExists(cardNum);
        if (focusAccount == null) {
            System.out.println("Such a card does not exist.");
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        String amount = scanner.nextLine();
        int tem = checkAmount(amount);
        if (tem != -1 && account.getBalance() >= tem) {
            focusAccount.setBalance(tem);
            SQLite.addIncome(focusAccount.getBalance(), cardNum);
            account.transferAmount(tem);
            SQLite.addIncome(account.getBalance(), account.getCard().getCardNum());
            System.out.println("Success!");
            return;
        }
        System.out.println("Not enough money!");



    }

    public void closeAccount(Account account) {
        SQLite.deleteAccount(account.getCard().getCardNum());
        System.out.println("The account has been closed!");
    }

    public boolean isLuhn(String cardNum) {
        int sum = 0;
        for (int i = 0; i < cardNum.length(); i++) {
            int parseInt = Integer.parseInt(String.valueOf(cardNum.charAt(i)));
            if (i % 2 == 0) {
                sum += parseInt * 2 > 9 ? (parseInt * 2) - 9 : parseInt * 2;
                continue;
            }
            sum += parseInt;
        }
        return sum % 10 == 0;
    }

    private int checkAmount(String amount) {
        int amountInt = 0;
        try {
            amountInt = Integer.parseInt(amount);
        } catch (InputMismatchException e) {
            System.out.println(amount + " is not valid num");
            return -1;
        }
        return amountInt;
    }


}
