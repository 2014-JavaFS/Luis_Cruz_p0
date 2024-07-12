package com.revature.rba.Member;

import com.revature.rba.util.exceptions.DataNotFoundException;
import com.revature.rba.util.interfaces.Serviceable;

import java.sql.SQLException;
import java.util.List;

public class MemberService implements Serviceable<Member> {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> findAll(){
        List<Member> members = memberRepository.findAll();
        if(members == null){
            throw new DataNotFoundException("No member information available");
        }
        else{
            return members;
        }
    }

    @Override
    public Member create() {
        return null;
    }

    public Member findUsingCredentials(String email, String password){
        Member member = memberRepository.findUsingCredentials(email, password);

        if(member == null){
            throw new DataNotFoundException("The credentials do not match what's on file");
        }
        else {
            return member;
        }
    }
}
