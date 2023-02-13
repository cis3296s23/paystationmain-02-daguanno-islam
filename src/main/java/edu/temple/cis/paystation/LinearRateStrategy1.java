package edu.temple.cis.paystation;

public class LinearRateStrategy1 implements RateStrategy {

    @Override
    public int calculateTime(int amount) {

        return amount / 5 * 2;
    }
}
