package com.revature.rba.Account;

import com.revature.rba.Member.Member;

public class Account {
    private int routingNumber;
    private int memberId;
    private AccountType type;
    private String accountName;


    private enum AccountType {
        CHECKING,
        SAVINGS,
        JOINT,
        // Add more types of accounts if needed
    }

    public Account(int routingNumber, int memberId,AccountType type){
        this.routingNumber = routingNumber;
        this.memberId = memberId;
        this.type = type;
        this.accountName = type.toString();
    }

    // polymorphism - overloading
    public Account(int routingNumber, int memberId, AccountType type, String accountName){
        this.routingNumber = routingNumber;
        this.type = this.type;
        this.accountName = accountName;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public int getMemberId() {
        return memberId;
    }

    public AccountType getType() {
        return type;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
