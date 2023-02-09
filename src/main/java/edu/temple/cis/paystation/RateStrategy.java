package edu.temple.cis.paystation;

public interface RateStrategy {
    /**
     * Return the parking time in minutes to be added to a customer's purchase,
     * based on the amount of money they have inserted into the PayStation.
     *
     * @param amount the amount of money inserted, in cents
     * @return the parking time added, in minutes
     */
    int calculateTime(int amount);
}
