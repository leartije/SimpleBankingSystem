package banking.services;

import banking.entity.Account;
import banking.entity.Card;
import banking.repository.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Scanner;

import static banking.constants.Text.*;

public class AccountService {
    private final Scanner scanner = new Scanner(System.in);
    private final Generate generate;
    private final SQLiteDatabase sqLiteDatabase;

    public AccountService() throws SQLException {
        this.generate = new Generate();
        this.sqLiteDatabase = new SQLiteDatabase();
    }

    public SQLiteDatabase getSqLite() {
        return sqLiteDatabase;
    }

    public void generateCard() {
        String cardNum = generate.generateCardNum();
        String pin = generate.generatePinNum();
        Account account = new Account(new Card(cardNum, pin), 0);
        sqLiteDatabase.saveAccount(
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
        Account searchAccount = sqLiteDatabase.loadAccount(cardNum, pin);
        if (searchAccount != null) {
            return searchAccount;
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
            account.addToBalance(income);
            sqLiteDatabase.changeAccountBalance(account.getBalance(), account.getCard().getCardNum());
            System.out.println(INCOME_ADDED);
        }
    }

    public void transfer(Account account) {
        if (account == null) {
            return;
        }
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
        Account focusAccount = sqLiteDatabase.ifExists(cardNum);
        if (focusAccount == null) {
            System.out.println(CARD_DONT_EXIST_ERROR);
            return;
        }
        System.out.println(ENTER_AMOUNT);
        int amount = checkAmount(scanner.nextLine());
        if (amount != -1 && account.getBalance() >= amount) {
            focusAccount.addToBalance(amount);
            sqLiteDatabase.changeAccountBalance(focusAccount.getBalance(), cardNum);
            account.transferAmount(amount);
            sqLiteDatabase.changeAccountBalance(account.getBalance(), account.getCard().getCardNum());
            System.out.println(SUCCESS);
            return;
        }
        if (amount == -1) {
            return;
        }
        System.out.println(NOT_ENOUGH_MONEY_ERROR);
    }

    public void closeAccount(Account account) {
        if (account == null) {
            return;
        }
        sqLiteDatabase.deleteAccount(account.getCard().getCardNum());
        System.out.println(CLOSE_ACCOUNT);
    }

    public void setupIdContinuity() {
        int lastId = sqLiteDatabase.getLastId();
        if (lastId != -1) {
            Account.id = lastId;
        }
    }

    private boolean isLuhn(String cardNum) {
        if (cardNum == null || cardNum.length() != 16) {
            return false;
        }

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
