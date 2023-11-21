package org.example.part1;

public class Dollar {
    public int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    public Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

    @Override
    public boolean equals(Object object) {

        if(object == null) {
            return false;
        }

        if(!(object instanceof Dollar)) {
            return false;
        }

        Dollar dollar= (Dollar) object;
        return amount == dollar.amount;
    }

    @Override
    public int hashCode() {
        return amount;
    }
}
