package com.revature.rba.Account;

import com.revature.rba.util.exceptions.DataNotFoundException;
import com.revature.rba.util.exceptions.InvalidInputException;
import com.revature.rba.util.interfaces.Serviceable;

import java.util.List;

public class AccountService implements Serviceable<Account> {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll(){
        List<Account> accounts = accountRepository.findAll();
        if(accounts == null){
            throw new DataNotFoundException("No account information available");
        }
        else {
            return accounts;
        }
    }

    @Override
    public Account create(Account account){
        Account accountCreated = accountRepository.create(account);
        if(accountCreated == null){
            throw new DataNotFoundException("Something went wrong. No account information was found");
        }
        return account;
    }
}
