package com.revature.rba;

import java.util.Scanner;

public class BankRunner {
    public static void main(String[] args){
        // TODO: Plan out the banking system and the classes we'll need to instantiate our objects.
        /*
            Plan:
                -Users will have accounts or references to accounts.
                -Before menu is shown, User will be prompted to sign in using some credentials
                    (i.e. email and password, or username and password)
                -Additionally there could be another option to create an account
         */
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        System.out.print("Welcome! ");
        do {
            // Menu
            System.out.println("Please select an option below!");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Create Account");
            System.out.println("6. Transfer Funds");
            System.out.println("7. Exit");
            System.out.println("\nEnter your choice: ");

            if(!scanner.hasNextInt()){
                System.out.println("Invalid input! Please select an option 1-7");
                continue; // GOTO start of loop
            }

            // User input
            input = scanner.nextInt();
            scanner.nextLine();

            // TODO: create models and controllers for Users and Accounts

            switch (input) {
                case 1:
                    // Deposit
                    break;
                case 2:
                    // Withdraw
                    break;
                case 3:
                    // View Balance
                    break;
                case 4:
                    // View Transaction History
                    break;
                case 5:
                    // Create Account
                    break;
                case 6:
                    // Transfer Funds
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please select an input 1-7");
            }
        } while(input != 7);

        System.out.println("\nThank you for using our services! Have a wonderful day!");
    }
}
