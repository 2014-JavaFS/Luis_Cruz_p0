package com.revature.rba.util.auth;

import com.revature.rba.Member.Member;
import com.revature.rba.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;

import javax.naming.AuthenticationException;

public class AuthController implements Controller {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("login", this::postLogin);
    }

    private void postLogin(Context ctx){
        String email = ctx.queryParam("email");
        String pass = ctx.queryParam("password");

        try{
            Member member = authService.login(email, pass);
            ctx.status(200);

        }catch (AuthenticationException e){
            e.printStackTrace();
        }
    }
}
