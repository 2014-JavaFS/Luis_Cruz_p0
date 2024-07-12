package com.revature.rba.Member;

import com.revature.rba.util.exceptions.DataNotFoundException;

import java.sql.SQLException;
import java.util.Scanner;

public class MemberController {
    public Scanner scanner;
    private final MemberService memberService;
    private Member member;

    public MemberController(Scanner scanner, MemberService memberService){
        this.scanner = scanner;
        this.memberService = memberService;
    }

    public Member login(){
        System.out.println("Please input your email");
        String email = scanner.next();
        System.out.println("Please input your password");
        String pass = scanner.next();

        try{
            this.member = memberService.findUsingCredentials(email, pass);
        } catch (DataNotFoundException e){
            return null;
        }

        return this.member;
    }

    public Member createNewUser(){
        return null;
    }

    public Member updateUserInfo(){
        return null;
    }

    public Member getCurrentUser(){
        return this.member;
    }




}
