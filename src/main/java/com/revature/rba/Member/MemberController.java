package com.revature.rba.Member;

import com.revature.rba.util.exceptions.DataNotFoundException;
import com.revature.rba.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class MemberController implements Controller {
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @Override
    public void registerPaths(Javalin app){
        app.get("/members", this::getAllMembers);
    }

    private List<Member> getAllMembers(Context ctx){
        List<Member> members = memberService.findAll();
        ctx.json(members);
        return members;
    }



}
