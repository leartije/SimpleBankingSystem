package banking.entity;

public class Account {

    private Card card;
    private double amount;

    public Account(Card card, double amount) {
        this.card = card;
        this.amount = amount;
    }

    public Card getCard() {
        return card;
    }

    public double getAmount() {
        return amount;
    }

}
