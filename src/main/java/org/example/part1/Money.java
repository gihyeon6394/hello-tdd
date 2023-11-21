package org.example.part1;

public abstract class Money {
    protected int amount;

    public static Money dollar(int amount) {
        return new Dollar(amount);
    }

    public static Money franc(int amount) {
        return new Franc(amount);
    }

    public abstract Money times(int multiplier);

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
