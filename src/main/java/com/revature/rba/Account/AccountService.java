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
        return accountCreated;
    }

    public boolean deposit(Account account){
        return accountRepository.deposit(account);
    }

    public boolean withdraw(Account account){
        return accountRepository.withdraw(account);
    }

    public List<Account> findFor(int id){
        List<Account> accounts = accountRepository.findAccountsFor(id);
        if(accounts == null){
            throw new DataNotFoundException("Unable to find that information");
        }
        return accounts;
    }

    public Account findAccountInfo(int routing){
        if(accountRepository.findById(routing) == null){
            throw new DataNotFoundException("Unable to find that information.");
        }

        return accountRepository.findById(routing);
    }
}
