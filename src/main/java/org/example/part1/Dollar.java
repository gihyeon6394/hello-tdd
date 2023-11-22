package org.example.part1;

public class Dollar extends Money {


    public Dollar(int amount, String currency) {
        super(amount, currency);
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    @Override
    public String toString() {
        return "Dollar{" +
                "currency='" + currency + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
