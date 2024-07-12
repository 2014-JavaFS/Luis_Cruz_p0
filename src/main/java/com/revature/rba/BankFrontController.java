package com.revature.rba;

import com.revature.rba.Member.MemberController;
import com.revature.rba.Member.MemberRepository;
import com.revature.rba.Member.MemberService;
import io.javalin.Javalin;

public class BankFrontController {
    public static void main(String[] args){
        // TODO: Plan out the banking system and the classes we'll need to instantiate our objects.
        /*
            Plan:
                -Users will have accounts or references to accounts.
                -Before menu is shown, User will be prompted to sign in using some credentials
                    (i.e. email and password, or username and password)
                -Additionally there could be another option to create an account
         */

        var app = Javalin.create().start();

        // Member
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);
        MemberController memberController = new MemberController(memberService);

        memberController.registerPaths(app);

        // Account

        // Auth

    }
}
