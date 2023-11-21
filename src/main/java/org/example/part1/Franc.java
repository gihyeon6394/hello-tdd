package org.example.part1;

public class Franc  extends Money{

    public Franc(int amount) {
        this.amount = amount;
    }

    @Override
    public Money times(int multiplier) {
        return new Franc(amount * multiplier);
    }


}
