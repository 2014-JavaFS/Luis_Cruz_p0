package com.revature.rba.Member;

import com.revature.rba.util.exceptions.DataNotFoundException;
import com.revature.rba.util.exceptions.InvalidInputException;
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
    public Member create(Member createMember) {
        return memberRepository.create(createMember);
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

    public boolean updateMember(Member member){

        return memberRepository.update(member);
    }

    private void validateAllInfo(Member member){

        if(member.getFirstName() == null){
            throw new InvalidInputException("First name must not be null");
        }

        if(!member.getEmail().contains("@") || !member.getEmail().contains(".") || member.getEmail() == null){
            throw new InvalidInputException("Email must not be null and must be valid");
        }

        if(member.getPassword() == null || !member.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")){
            throw new InvalidInputException("Password must not be null, must contain at least 1 number, 1 lower case, 1 upper case, have a special character (@#$%^&+=), contain no whitespace, and be 8 characters.");
        }
    }

    public Member getMemberById(int id){
        return memberRepository.findById(id);

    }
}
