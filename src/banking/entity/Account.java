package banking.entity;

public class Account {

    private static int id;
    private final int accountId;
    private final Card card;
    private int amount;

    public Account(Card card, int amount) {
        this.card = card;
        this.amount = amount;
        this.accountId = ++id;
    }

    public Card getCard() {
        return card;
    }

    public int getAmount() {
        return amount;
    }

    public int getAccountId() {
        return accountId;
    }
}
