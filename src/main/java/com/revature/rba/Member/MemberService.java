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
        validateAllInfo(createMember);
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
        validateAllInfo(member);
        return memberRepository.update(member);
    }

    private void validateAllInfo(Member member){

        if(!member.getEmail().contains("@") || !member.getEmail().contains(".") || member.getEmail() == null || member.getEmail().isBlank()){
            throw new InvalidInputException("Email must not be null and must be valid");
        }

        if(member.getPassword().isBlank() || member.getPassword() == null){
            throw new InvalidInputException("Password can be anything just not blank >:(");
        }
    }

    public Member getMemberById(int id){
        Member member = memberRepository.findById(id);

        if(member == null){
            throw new DataNotFoundException("The information could not be found in the database");
        }

        return member;
    }

    public boolean deleteMember(int id){
        return memberRepository.delete(id);
    }
}
