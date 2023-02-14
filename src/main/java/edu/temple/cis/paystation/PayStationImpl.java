package edu.temple.cis.paystation;
import java.util.*;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar, timeBought, totalMoney;
    private Map<Integer, Integer> coinMap;

    // Constructor initializes instance variables
    public PayStationImpl(){
        insertedSoFar = timeBought = totalMoney = 0;
        coinMap = new HashMap<>();
    }
    
    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {

        switch (coinValue) {
            case 5:
            case 10:
            case 25:
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }

        /*
         * getOrDefault checks if a given key is present in a map
         * @returns the value if it exists, or the 'defaultValue' if it does not
         * add 1 to whatever the result of getOrDefault is and place that value in the map
         */
        coinMap.put(coinValue, coinMap.getOrDefault(coinValue, 0) + 1);

        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalMoney += insertedSoFar;
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() 
    {
        Map<Integer, Integer> tempMap = coinMap;
        coinMap = new HashMap<>();
        reset();
        return tempMap;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        coinMap.clear();
    }
    
    @Override
    public int empty()
    {
        int temp = totalMoney;
        totalMoney = 0;
        return temp;
    }

    public static void main(String[] args) {

        // Town Choice Input
        System.out.println("Select the town which you reside, numerical input only:\n" + "1 - AlphaTown\n" + "2 - BetaTown\n" + "3 - GammaTown\n" + "4 - DeltaTown\n" + "5 - OmegaTown\n");
        Scanner console = new Scanner(System.in);
        int townChoice = console.nextInt();
        while ((townChoice < 1) || (townChoice > 5)) {
            System.out.println("Invalid input; please reselect town. ");
            townChoice = console.nextInt();
        }
        System.out.println("You have selected: " + townChoice);

        // Menu Options
        System.out.println("Choose an option:\n1) Deposit coins\n2) Display Time Bought\n3) Buy Ticket\n4) Admin Options\n");
        int optionChoice = console.nextInt();
        while ((optionChoice < 1) || (optionChoice > 4)) {
            System.out.println("Invalid input; please reselect valid option. ");
            optionChoice = console.nextInt();
        }

        switch (optionChoice) {
            case 1:
            System.out.println("Deposit coins here; valid coins are 5, 10, 25. Type 'continue' when done depositing coins: ");
            int coin;
            boolean done = false;
            while () {
                
            }
        }

        // If Admin Chosen, validate password.
        if (optionChoice == 4) {
            System.out.print("Please enter admin password: ");
            int passwordEntry = console.nextInt();
            if (passwordEntry == 123) {
                System.out.println("Access granted, Please select one of the following options:\n1) Empty\n2) Change Rate Strategy");
            } else {
                System.out.println("Invalid password, failsafe protection activating; paystation shutting off");
                System.exit(0);
            }
            optionChoice = console.nextInt();
            // TODO: admin options
        }
    }
}
