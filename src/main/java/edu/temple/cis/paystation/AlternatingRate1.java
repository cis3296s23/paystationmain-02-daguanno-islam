package edu.temple.cis.paystation;
import java.util.Calendar;

public class AlternatingRate1 implements RateStrategy {

    final private int dayOfWeek;

    public AlternatingRate1(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public int calculateTime(int amount) {
        //if the date is a saturday or sunday then linear1 rate
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return (amount * 2) / 5;
        } else {//if a weekday then progressive rate
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
}
