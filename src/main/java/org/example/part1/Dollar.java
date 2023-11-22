package org.example.part1;

public class Dollar extends Money {

    private String currency;
    public Dollar(int amount, String currency) {
        super(amount, currency);
    }

    @Override
    public Money times(int multiplier) {
        return Money.dollar(amount * multiplier);
    }

    @Override
    String currency() {
        return currency;
    }


}
