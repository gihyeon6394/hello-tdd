package org.example.part1;

public class Franc {
    public int amount;

    public Franc(int amount) {
        this.amount = amount;
    }

    public Franc times(int multiplier) {
        return new Franc(amount * multiplier);
    }

    @Override
    public boolean equals(Object object) {

        if(object == null) {
            return false;
        }

        if(!(object instanceof Franc)) {
            return false;
        }

        Franc dollar= (Franc) object;
        return amount == dollar.amount;
    }

    @Override
    public int hashCode() {
        return amount;
    }
}
