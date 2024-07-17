package com.revature.rba.Account;

import com.revature.rba.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AccountController implements Controller {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app){
        app.get("/accounts", this::getAllAccounts);
        app.post("/accounts/{memberId}", this::postAccount);
    }

    private List<Account> getAllAccounts(Context ctx){
        String memberType = ctx.header("memberType");

        if(memberType == null || !memberType.equals("ADMIN")){
            ctx.status(403);
            ctx.result("You are not allowed to view all accounts.");
            return null;
        }

        List<Account> accounts = accountService.findAll();
        ctx.json(accounts);
        return accounts;
    }

    private void postAccount(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        int memberId = Integer.parseInt(ctx.pathParam("memberId"));
        String memberType = ctx.header("memberType");

        if(memberType == null || (memberId != account.getMemberId() && memberType.equals("USER"))){
            ctx.status(403);
            ctx.result("You cannot create this account.");
            return;
        }

        accountService.create(account);
    }
}
