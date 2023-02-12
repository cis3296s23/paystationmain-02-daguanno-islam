package edu.temple.cis.paystation;

public class ProgressiveRateStrategy implements RateStrategy{
    @Override
    public int calculateTime(int amount) {

        if (amount < 150) {
            return (amount * 2) / 5;
        }
        else if ((amount >= 150) && (amount < 350)) {
            double d = ((amount - 150) * 0.3) + 60;
            return (int) d;
        }
        else {
            return (amount - 350) / 5 + 120;
        }
    }
}
