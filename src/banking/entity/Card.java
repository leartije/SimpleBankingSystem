package banking.entity;

import java.util.Objects;

public class Card {

    private final String cardNum;
    private final String pin;

    public Card(String cardNum, String pin) {
        this.cardNum = cardNum;
        this.pin = pin;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getPin() {
        return pin;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNum='" + cardNum + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardNum, card.cardNum) && Objects.equals(pin, card.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNum, pin);
    }
}
