package edu.temple.cis.paystation;
import java.util.*;
import java.util.Calendar;


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

    private RateStrategy rateStrategy;

    // Constructor initializes instance variables
    public PayStationImpl(){
        insertedSoFar = timeBought = totalMoney = 0;
        coinMap = new HashMap<>();
        rateStrategy = new LinearRateStrategy1();
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
        //The following code assigns timeBought from the current rateStrategy's calculateTime function
        timeBought = rateStrategy.calculateTime(coinValue);
        //timeBought = insertedSoFar / 5 * 2;
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
    /*
    the following function changes the rate strategy at run time. The user is prompted to
    pick which rate strategy to use and then the rate strategy is changed from the default
    strategy.
     */
    @Override
    public void changeRateStrategy(RateStrategy newRateStrategy){
        this.rateStrategy = newRateStrategy;
    }

    public void main(String[] args) throws IllegalCoinException {
        Scanner scanner = new Scanner(System.in);

        int choice;
        //this code uses a do-while loop for the options
        do {
            //print out the different options for the user and save as an int
            System.out.println("\nPlease select a choice:");
            System.out.println("1. Deposit Coins");
            System.out.println("2. Display");
            System.out.println("3. Buy Ticket");
            System.out.println("4. Cancel");
            System.out.println("5. Empty (Admin)");
            System.out.println("6. Change Rate Strategy (Admin)");
            System.out.print("Enter choice (1-6): ");

            choice = scanner.nextInt();//get the choice

            switch (choice) {//switch case for the options
                case 1: //Depositing coins
                    System.out.println("Deposit Coins selected");
                    System.out.println("Enter the coin value (5, 10, 25) and press enter. To stop depositing coins, type done without entering a value:");
                    int coinValue;
                    while (true) {//this will loop until the user enters done
                        if (scanner.hasNextInt()) {//get the coin  value, add this coin then repeat
                            coinValue = scanner.nextInt();
                            addPayment(coinValue);
                            System.out.println("Enter the next coin value (5, 10, 25) or type done to stop depositing coins:");
                        } else {
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Display selected");
                    int display = readDisplay();
                    System.out.println("Time purchased: " + display + " minutes");
                    break;
                case 3:
                    System.out.println("Buy Ticket selected");
                    Receipt receipt = buy();
                    //receipt object created from the buy() function
                    System.out.println("Parking receipt purchased.");
                    System.out.println("Receipt valid for " + receipt.value() + " minutes.");
                    break;
                case 4:
                    System.out.println("Cancel selected");
                    break;
                case 5:
                    System.out.println("Empty (Admin) selected");
                    empty();
                    System.out.println("Pay Station has been emptied");
                    break;
                case 6:
                    System.out.println("Change Rate Strategy (Admin) selected");
                    System.out.println("Please choose one of the following options: ");
                    System.out.println("1. Alphatown");
                    System.out.println("2. Betatown");
                    System.out.println("3. Gammatown");
                    System.out.println("4. Deltatown");
                    System.out.println("5. Omegatown");

                    int townChoice = scanner.nextInt();

                    switch (townChoice) {
                        case 1:
                            System.out.println("You have selected Alphatown");
                            rateStrategy = new LinearRateStrategy1();
                            changeRateStrategy(rateStrategy);
                            break;
                        case 2:
                            System.out.println("You have selected Betatown");
                            break;
                        case 3:
                            System.out.println("You have selected Gammatown");
                            break;
                        case 4:
                            System.out.println("You have selected Deltatown");
                            break;
                        case 5:
                            System.out.println("You have selected Omegatown");
                            break;
                        default:
                            System.out.println("Invalid selection. Please try again.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        while (choice != 4);//menu will loop until 4 is chosen

        System.out.println("Exiting program...");
    }

}
