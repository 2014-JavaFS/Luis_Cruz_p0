package com.revature.rba.Account;

import com.revature.rba.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AccountController implements Controller {
    AccountService accountService;

    @Override
    public void registerPaths(Javalin app){
        app.get("/accounts", this::getAllAccounts);
    }

    private List<Account> getAllAccounts(Context ctx){
        List<Account> accounts = accountService.findAll();
        ctx.json(accounts);
        return accounts;
    }
}
