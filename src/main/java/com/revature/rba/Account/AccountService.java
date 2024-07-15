package com.revature.rba.Account;

import com.revature.rba.util.exceptions.DataNotFoundException;
import com.revature.rba.util.interfaces.Serviceable;

import java.util.List;

public class AccountService implements Serviceable<Account> {
    AccountRepository accountRepository;

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
    public Account create(){
        return null;
    }
}
