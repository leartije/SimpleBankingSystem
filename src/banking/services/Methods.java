package banking.services;

import banking.entity.Account;
import banking.entity.Card;
import banking.repository.Database;

import java.util.Optional;
import java.util.Scanner;

import static banking.constants.Text.*;

public class Methods {
    private final Scanner scanner = new Scanner(System.in);
    private final Generate generate;

    public Methods() {
        this.generate = new Generate();
    }

    public void generateCard() {
        String cardNum = generate.generateCardNum();
        String pin = generate.generatePinNum();
        Account account = new Account(new Card(cardNum, pin), 0);
        Database.accountList.add(account);
        System.out.printf(CARD_HAS_BEEN_CREATED, cardNum, pin);
    }

    public Account login() {
        System.out.println(ENTER_YOUR_CARD_NUMBER);
        String cardNum = scanner.nextLine();
        System.out.println(ENTER_YOUR_PIN);
        String pin = scanner.nextLine();
        Card temp = new Card(cardNum, pin);
        Optional<Account> any = Database.accountList
                .stream()
                .filter(account -> account.getCard().equals(temp))
                .findAny();

        if (any.isPresent()) {
            return any.get();
        }
        System.out.println(WRONG_LOGIN_INF);
        return null;
    }





}
