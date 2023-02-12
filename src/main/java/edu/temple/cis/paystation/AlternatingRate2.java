package edu.temple.cis.paystation;
import java.util.Calendar;

public class AlternatingRate2 implements RateStrategy {

    final private int dayOfWeek;

    public AlternatingRate2(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
//need to fix below
    @Override
    public int calculateTime(int amount) {
        //if it is a week day, then do the linear1 strategy
        if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
            return amount / 5 * 2;
        } else {
            return 0;
        }
    }
}
