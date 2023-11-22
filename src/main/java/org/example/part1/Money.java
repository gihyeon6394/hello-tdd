package org.example.part1;

public abstract class Money {
    protected int amount;
    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }
    public static Money dollar(int amount) {
        return new Dollar(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Franc(amount, "CHF");
    }

    public abstract Money times(int multiplier);

    String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object object) {

        if (object == null) {
            return false;
        }

        if (!(object instanceof Money)) {
            return false;
        }

        Money money = (Money) object;
        return amount == money.amount && getClass().equals(money.getClass());
    }

    @Override
    public int hashCode() {
        return amount;
    }
}
