package com.revature.rba.Member;

import com.revature.rba.util.exceptions.DataNotFoundException;
import com.revature.rba.util.exceptions.InvalidInputException;
import com.revature.rba.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import javax.xml.crypto.Data;
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
        app.put("/members/{memberId}", this::putUpdateMember);
        app.delete("/members/{memberId}", this::deleteMemberById);
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

        if(member.getType().equals("ADMIN") && memberType != null && !memberType.equals("ADMIN")){
            ctx.status(403);
            ctx.result("An admin account must be updated by an admin. Please log into your admin account to change your information.");
        }

        int id = Integer.parseInt(ctx.pathParam("memberId"));

        if(memberType == null || memberType.equals("USER")){
            ctx.status(403);
            ctx.result("You do not have permission to update a member");
            return;
        }
        try{
            if(memberService.updateMember(member, id)){
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
        int id = Integer.parseInt(ctx.pathParam("memberId"));
        String memberType = ctx.header("memberType");
        int memberId = Integer.parseInt(ctx.header("memberId"));

        if(memberType == null || memberType.equals("USER") && memberId != id){
            ctx.status(HttpStatus.FORBIDDEN);
            ctx.result("You do not have permission to ");
            return;
        }

        try{
            Member foundMember = memberService.getMemberById(id);
            ctx.json(foundMember);
        }catch (DataNotFoundException e){
            ctx.status(HttpStatus.NOT_FOUND);
        }catch (RuntimeException e){
            ctx.status(500);
        }
    }

    private void deleteMemberById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("memberId"));
        String memberType = ctx.header("memberType");


    }



}
