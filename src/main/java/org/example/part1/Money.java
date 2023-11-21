package org.example.part1;

public class Money {
    protected int amount;

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
