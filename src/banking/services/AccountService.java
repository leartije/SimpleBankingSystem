package banking.services;

import banking.entity.Account;
import banking.entity.Card;
import banking.repository.SQLite;

import java.sql.SQLException;
import java.util.Scanner;

import static banking.constants.Text.*;

public class AccountService {
    private final Scanner scanner = new Scanner(System.in);
    private final Generate generate;
    private final SQLite sqLite;

    public AccountService() throws SQLException {
        this.generate = new Generate();

        this.sqLite = new SQLite();
    }

    public SQLite getSqLite() {
        return sqLite;
    }

    public void generateCard() {
        String cardNum = generate.generateCardNum();
        String pin = generate.generatePinNum();
        Account account = new Account(new Card(cardNum, pin), 0);
        sqLite.saveAccount(
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
        Account search = sqLite.loadAccount(cardNum, pin);
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
        System.out.println(ENTER_INCOME);
        int income = checkAmount(scanner.nextLine());
        if (income != -1) {
            account.setBalance(income);
            sqLite.addIncome(account.getBalance(), account.getCard().getCardNum());
            System.out.println(INCOME_ADDED);
        }
    }

    public void transfer(Account account) {
        System.out.println(TRANSFER);
        System.out.println(ENTER_CARD_NUMBER);
        String cardNum = scanner.nextLine();
        if (cardNum.equals(account.getCard().getCardNum())) {
            System.out.println(SAME_ACC_ERROR);
            return;
        }
        if (!isLuhn(cardNum)) {
            System.out.println(NO_LUHN_NUM_ERROR);
            return;
        }
        Account focusAccount = sqLite.isExists(cardNum);
        if (focusAccount == null) {
            System.out.println(CARD_DONT_EXIST_ERROR);
            return;
        }
        System.out.println(ENTER_AMOUNT);
        int amount = checkAmount(scanner.nextLine());
        if (amount != -1 && account.getBalance() >= amount) {
            focusAccount.setBalance(amount);
            sqLite.addIncome(focusAccount.getBalance(), cardNum);
            account.transferAmount(amount);
            sqLite.addIncome(account.getBalance(), account.getCard().getCardNum());
            System.out.println(SUCCESS);
            return;
        }
        if (amount == -1) {
            return;
        }
        System.out.println(NOT_ENOUGH_MONEY_ERROR);
    }

    public void closeAccount(Account account) {
        sqLite.deleteAccount(account.getCard().getCardNum());
        System.out.println(CLOSE_ACCOUNT);
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
        try {
            int parse = Integer.parseInt(amount);
            if (parse >= 0) {
                return parse;
            }
            throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println(amount + " is not valid num");
            return -1;
        }
    }


}
