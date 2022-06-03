package banking.entity;

import java.util.Objects;

public class Account {

    private static int id;
    private final int accountId;
    private final Card card;
    private int balance;

    public Account(Card card, int balance) {
        this.card = card;
        this.balance = balance;
        this.accountId = ++id;
    }

    public Card getCard() {
        return card;
    }

    public int getBalance() {
        return balance;
    }

    public void addToBalance(int balance) {
        this.balance += balance;
    }

    public void transferAmount(int amount) {
        this.balance -= amount;
    }

    public int getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", card=" + card +
                ", amount=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && balance == account.balance && Objects.equals(card, account.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, card, balance);
    }
}
