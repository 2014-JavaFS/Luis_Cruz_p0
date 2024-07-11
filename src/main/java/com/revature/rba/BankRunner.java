package com.revature.rba;

import com.revature.rba.Member.Member;
import com.revature.rba.Member.MemberController;
import com.revature.rba.Member.MemberRepository;
import com.revature.rba.Member.MemberService;

import javax.sound.midi.MetaMessage;
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
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);
        MemberController memberController = new MemberController(scanner, memberService);
        System.out.print("Welcome! ");

        do{
            System.out.println("Please select an option:");
            System.out.println("1. Login (existing user)");
            System.out.println("2. Create new user");
            System.out.println("3. Exit");

            if(!scanner.hasNextInt()){
                System.out.println("Invalid input! Please select an option 1-3");
            }

            input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 1:
                    if(memberController.login() != null){
                        System.out.println("Successfully logged in!");
                        showUserActions(scanner);
                    }
                    else{
                        System.out.println("That information does not match our records. Please make sure you are inputting your email/password correctly and try again.");
                    }
                    break;
                case 2:
                    //Create new Member
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input! Please select an option 1-3!");
            }
        }while(input != 3);

        System.out.println("\nThank you for using our services! Have a wonderful day!");
    }

    public static void showUserActions(Scanner scanner){
        int input = 0;
        do {
            // Menu
            System.out.println("Please select an option below!");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Create Account");
            System.out.println("6. Transfer Funds");
            System.out.println("7. Update User Info");
            System.out.println("8. Update Account Info");
            System.out.println("7. Logout");
            System.out.println("\nEnter your choice: ");

            if(!scanner.hasNextInt()){
                System.out.println("Invalid input! Please select an option 1-7");
                continue; // GOTO start of loop
            }

            // User input
            input = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please select an input 1-7");
            }
        } while(input != 7);
    }
}
