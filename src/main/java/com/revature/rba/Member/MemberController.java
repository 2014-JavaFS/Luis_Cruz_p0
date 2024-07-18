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
        app.get("/members/view-member/{memberId}", this::getMemberById);
        app.post("/members/create-member", this::postMember);
        app.put("/members/update-member-info/{memberId}", this::putNewInfo);
        app.put("/members/change-user-type/{memberId}", this::putNewUserType);
        app.delete("/members/delete/{memberId}", this::deleteMember);
    }

    private void getAllMembers(Context ctx){
        String memberType = ctx.header("memberType");

        if(memberType == null || !memberType.equals("ADMIN")){
            ctx.status(403);
            ctx.result("You do not have the permissions required to perform this action.");
            return;
        }

        List<Member> members = memberService.findAll();
        if(members != null){
            ctx.status(200);
            ctx.result("All members: ");
            ctx.json(members);
            return;
        }

        ctx.status(500);
        ctx.result("Could not find member information");
    }

    private void postMember(Context ctx){
        String memberType = ctx.header("memberType");
        Member createMember = ctx.bodyAsClass(Member.class);

        if(memberType == null || memberType.equals("USER")){
            ctx.status(403);
            ctx.result("You do not have permission to create a new member");
            return;
        }

        Member member = memberService.create(createMember);

        if(member != null){
            ctx.status(200);
            ctx.result("Successfully created member!");
            ctx.json(member);
            return;
        }

        ctx.status(500);
        ctx.result("Could not create new member");

    }

    private void putNewInfo(Context ctx){
        String memberType = ctx.header("memberType");
        int memberId = Integer.parseInt(ctx.header("memberId"));
        Member member = ctx.bodyAsClass(Member.class);

        if(member.getType().equals("ADMIN") && (memberType == null || !memberType.equals("ADMIN"))){
            ctx.status(403);
            ctx.result("An admin account must be updated by an admin. Please log into your admin account to change your information.");
        }

        boolean executed = memberService.updateMember(member);

        if(executed){
            ctx.status(200);
            ctx.result("Successfully updated member!");
            return;
        }

        ctx.status(500);
        ctx.result("Something went wrong. Please make sure the information entered is correct.");
    }

    private void getMemberById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("memberId"));
        String memberType = ctx.header("memberType");
        int memberId = Integer.parseInt(ctx.header("memberId"));

        if(memberType == null || (memberType.equals("USER") && memberId != id)){
            ctx.status(HttpStatus.FORBIDDEN);
            ctx.result("You do not have permission to view this member's information");
            return;
        }

        Member member = memberService.getMemberById(id);

        if(member == null){
            ctx.status(500);
            ctx.result("Could not find member");
            return;
        }

        ctx.status(200);
        ctx.result("User information: ");
        ctx.json(member);
    }

    private void deleteMember(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("memberId"));
        String memberType = ctx.header("memberType");

        if(memberType == null || !memberType.equals("ADMIN")){
            ctx.status(403);
            ctx.result("You are not allowed to delete this member. If you're trying to leave our bank, please call (555) 555-5555 or go to your local office to begin that process");
        }

        boolean execute = memberService.deleteMember(id);

        if(execute){
            ctx.status(200);
            ctx.result("Successfully deleted member.");
            return;
        }

        ctx.status(500);
        ctx.result("Could not delete member. Please ensure the id is correct.");

    }

    private void putNewUserType(Context ctx){

    }

}
