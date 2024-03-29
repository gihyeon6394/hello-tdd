package org.example.part1;

public class Money implements Expression {
    protected int amount;
    protected String currency;

    public Money(int amount, String currency) {
        this.currency = currency;
        this.amount = amount;
    }

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public String currency() {
        return currency;
    }

    public Money reduce(String to) {
        return this;
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
        return amount == money.amount
//                && getClass().equals(money.getClass())
                && currency().equals(money.currency());
    }


    public Expression plus(Money addend) {
        return new Sum(this, addend);
    }


    @Override
    public int hashCode() {
        return amount;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
