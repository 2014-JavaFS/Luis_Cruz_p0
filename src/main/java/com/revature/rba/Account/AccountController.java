package com.revature.rba.Account;

import com.revature.rba.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

public class AccountController implements Controller {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app){
        app.get("/accounts", this::getAllAccounts);
        app.post("/accounts/create/{memberId}", this::postAccount);
        app.put("/accounts/deposit", this::putDeposit);
        app.put("/accounts/withdraw", this::putWithdraw);
        app.get("/accounts/view-all-user-accounts/{memberId}", this::getMyAccounts);
        app.get("/accounts/view-account/{routingNumber}", this::getBalance);
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

    private void putDeposit(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        int memberId = Integer.parseInt(ctx.header("memberId"));
        String memberType = ctx.header("memberType");

        if(memberType == null || memberId != account.getMemberId()){
            if(memberType.equals("USER")){
                ctx.status(403);
                ctx.result("You cannot deposit into this account");
                return;
            }
        }

        boolean executed = accountService.deposit(account);

        if(executed){
            ctx.status(200);
            ctx.result("Deposit of " + "$" + account.getAmount() + " successful!");
            return;
        }

        ctx.status(403);
        ctx.result("Unable to perform operation. Make sure information was inputted correctly");
    }

    private void putWithdraw(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        int memberId = Integer.parseInt(ctx.header("memberId"));
        String memberType = ctx.header("memberType");

        if(memberType == null || memberId != account.getMemberId()){
            if(memberType.equals("USER")){
                ctx.status(403);
                ctx.result("You cannot withdraw from this account");
                return;
            }
        }

        boolean executed = accountService.withdraw(account);

        if(executed){
            ctx.status(HttpStatus.OK);
            ctx.result("Successfully withdrew " + "$" + account.getAmount());
            return;
        }

        ctx.status(500);
        ctx.result("Could not withdraw from account. Ensure the information you input is correct.");
    }

    private void getBalance(Context ctx){
        String memberType = ctx.header("memberType");
        int memberId = Integer.parseInt(ctx.header("memberId"));
        int routingNumber = Integer.parseInt(ctx.pathParam("routingNumber"));

        if(memberType == null){
            ctx.status(403);
            ctx.result("You do not have access to view these accounts");
            return;
        }

        Account account = accountService.findAccountInfo(routingNumber);

        if(account.getMemberId() != memberId){
            ctx.status(403);
            ctx.result("You are not allowed to view this account.");
            return;
        }

        ctx.status(200);
        ctx.json(account);
    }

    private void getMyAccounts(Context ctx){
        String memberType = ctx.header("memberType");
        int memberId = Integer.parseInt(ctx.header("memberId"));
        int accessId = Integer.parseInt(ctx.pathParam("memberId"));

        if(memberType == null || (memberId != accessId && memberType.equals("USER"))){
            ctx.status(403);
            ctx.result("You do not have access to view these accounts");
            return;
        }

        ctx.json(accountService.findFor(memberId));
    }
}
