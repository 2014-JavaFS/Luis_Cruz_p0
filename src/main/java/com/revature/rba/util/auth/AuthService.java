package com.revature.rba.util.auth;

import com.revature.rba.Member.Member;

import javax.naming.AuthenticationException;

public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public Member login(String email, String pass) throws AuthenticationException {
        Member member = authRepository.login(email, pass);

        if(member == null){
            throw new AuthenticationException("Login credentials were incorrect. Please try again.");
        }
        else{
            return member;
        }
    }
}
