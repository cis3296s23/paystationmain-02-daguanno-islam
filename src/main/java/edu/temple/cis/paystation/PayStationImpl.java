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
        Scanner scanner = new Scanner(System.in);

        int choice;
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
                case 1:
                    System.out.println("Deposit Coins selected");
                    break;
                case 2:
                    System.out.println("Display selected");
                    break;
                case 3:
                    System.out.println("Buy Ticket selected");
                    break;
                case 4:
                    System.out.println("Cancel selected");
                    break;
                case 5://case 5 and 6 both require a password as an admin, password is 1234
                case 6:
                    System.out.print("Enter password: ");
                    String password = scanner.next();
                    if (password.equals("1234")) {
                        if (choice == 5) {
                            System.out.println("Empty (Admin) selected");
                        }
                        else {
                            System.out.println("Change Rate Strategy (Admin) selected");
                        }
                    }
                    else {
                        System.out.println("Invalid password. Access denied.");
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
