package com.revature.rba;

import com.revature.rba.Account.AccountController;
import com.revature.rba.Account.AccountRepository;
import com.revature.rba.Account.AccountService;
import com.revature.rba.Member.MemberController;
import com.revature.rba.Member.MemberRepository;
import com.revature.rba.Member.MemberService;
import com.revature.rba.util.auth.AuthController;
import com.revature.rba.util.auth.AuthRepository;
import com.revature.rba.util.auth.AuthService;
import io.javalin.Javalin;
import org.slf4j.LoggerFactory;

public class BankFrontController {
    public static void main(String[] args){
        var app = Javalin.create().start();

        // Member
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);
        MemberController memberController = new MemberController(memberService);

        memberController.registerPaths(app);

        // Account
        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        AccountController accountController = new AccountController(accountService);

        accountController.registerPaths(app);

        // Auth
        AuthRepository authRepository = new AuthRepository();
        AuthService authService = new AuthService(authRepository);
        AuthController authController = new AuthController(authService);

        authController.registerPaths(app);
    }
}
