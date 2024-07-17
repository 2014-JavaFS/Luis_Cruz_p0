package com.revature.rba.Member;

import com.revature.rba.util.exceptions.DataNotFoundException;
import com.revature.rba.util.exceptions.InvalidInputException;
import com.revature.rba.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;


public class MemberController implements Controller {
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @Override
    public void registerPaths(Javalin app){
        app.get("/members", this::getAllMembers);
        app.get("/members/{memberId}", this::getMemberById);
        app.post("/members", this::postMember);
        app.put("members", this::putUpdateMember);
    }

    private List<Member> getAllMembers(Context ctx){
        String memberType = ctx.header("memberType");

        if(memberType != null && !memberType.equals("ADMIN")){
            ctx.status(403);
            ctx.result("You do not have the permissions required to perform this action.");
        }
        else{
            List<Member> members = memberService.findAll();
            ctx.json(members);
            return members;
        }
        return null;
    }

    private void postMember(Context ctx){
        String memberType = ctx.header("memberType");

        Member createMember = ctx.bodyAsClass(Member.class);

        if(memberType == null || memberType.equals("USER")){
            ctx.status(403);
            ctx.result("You do not have permission to create a new member");
            return;
        }

        memberService.create(createMember);
    }

    private void putUpdateMember(Context ctx){
        String memberType = ctx.header("memberType");

        Member member = ctx.bodyAsClass(Member.class);

        if(memberType == null || memberType.equals("USER")){
            ctx.status(403);
            ctx.result("You do not have permission to update a member");
            return;
        }
        try{
            if(memberService.updateMember(member)){
                ctx.status(HttpStatus.ACCEPTED);
            }
            else{
                ctx.status(HttpStatus.BAD_REQUEST);
            }
        } catch(InvalidInputException e){
            e.printStackTrace();
        }

    }

    private void getMemberById(Context ctx){
        int id = Integer.parseInt(ctx.queryParam("memberId"));

        if(memberService.getMemberById(id) == null){
            throw new DataNotFoundException("That user could not be found");
        }

        ctx.json(memberService.getMemberById(id));
    }



}
