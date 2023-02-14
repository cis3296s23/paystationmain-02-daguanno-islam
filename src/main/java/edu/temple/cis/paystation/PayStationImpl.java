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

    public static void main(String[] args) throws IllegalCoinException {
        PayStationImpl ps = new PayStationImpl();
        boolean complete = false;

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
        int optionChoice;
        do {
            System.out.println("");
            System.out.println("Choose an option:");
            System.out.println("1) Deposit coins");
            System.out.println("2) Display Time Bought");
            System.out.println("3) Buy Ticket");
            System.out.println("4) Cancel");
            System.out.println("5) Empty (Admin)");
            System.out.println("6) Change Rate Strategy (Admin)");
            System.out.println("7) Exit");
            optionChoice = console.nextInt();
            while ((optionChoice < 1) || (optionChoice > 7)) {
                System.out.println("Invalid input; please reselect valid option. ");
                optionChoice = console.nextInt();
            }

            if (optionChoice == 1) {
                System.out.println("Deposit coins here; valid coins are 5, 10, 25.");
                int coin;
                boolean done = false;
                while (!done) {
                    coin = console.nextInt();
                    if (coin == 0) {
                        System.out.println("Coins sucessfully deposited.");
                        break;
                    } else {
                        ps.addPayment(coin);
                        System.out.println("Payment added. Continue entering coins or press 0 to exit: ");
                    }
                }

            } else if (optionChoice == 2) {
                System.out.println("You selected the display.");
                int display = ps.readDisplay();
                System.out.println("Total time purchased: " + display + " minutes.");

            } else if (optionChoice == 3) {
                System.out.println("Buy Ticket selected.");
                Receipt receipt = ps.buy();
                System.out.println("Parking receipt purchased.");
                System.out.println("Receipt valid for " + receipt.value() + " minutes.");

            } else if (optionChoice == 4) {
                System.out.println("Cancel selected.\nHere are your coins back: " + ps.cancel());

            } else if ((optionChoice == 5) || (optionChoice == 6)) {
                System.out.print("Please enter admin password: ");
                int passwordEntry = console.nextInt();
                if (passwordEntry == 123) {
                    System.out.println("Access granted");
                } else {
                    System.out.println("Invalid password, failsafe protection activating; paystation shutting off");
                    System.exit(0);
                }
                if (optionChoice == 5) {
                    ps.empty();
                    System.out.println("Pay Station has been emptied");
                }
                if (optionChoice == 6) {
                    System.out.println("Change Rate Strategy (Admin) selected");
                    System.out.println("Please choose one of the following options: ");
                    System.out.println("1. Alphatown");
                    System.out.println("2. Betatown");
                    System.out.println("3. Gammatown");
                    System.out.println("4. Deltatown");
                    System.out.println("5. Omegatown");                }
            } else if (optionChoice == 7) {
                System.exit(0);
            }
        } while (!complete);
    }
}
