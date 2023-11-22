package org.example.part1;

public class Franc extends Money {

    public Franc(int amount, String currency) {
        super(amount, currency);
    }


    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    @Override
    public String toString() {
        return "Franc{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
