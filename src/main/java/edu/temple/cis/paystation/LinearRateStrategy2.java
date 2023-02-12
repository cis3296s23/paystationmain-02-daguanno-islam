package edu.temple.cis.paystation;

public class LinearRateStrategy2 implements RateStrategy {

    @Override
    public int calculateTime(int amount) {
        return amount / 5;
    }
}
