package com.revature.rba.Member;

/*
    Follows DAO pattern (Data Access Object)
 */

import com.revature.rba.util.ConnectionFactory;
import com.revature.rba.util.interfaces.Crudable;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository implements Crudable<Member> {
    @Override
    public List<Member> findAll() {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            List<Member> members = new ArrayList<>();

            String sql = "select * from flights";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()){
                Member member = new Member();

                if(members.isEmpty()) {
                    member.setMemberId(rs.getInt("user_id"));
                    member.setEmail(rs.getString("email"));
                    member.setPassword(rs.getString("password"));
                    member.setType(Member.MemberType.valueOf(rs.getString("member_type")));
                }
                else{
                    members.add(member);
                }
            }

            return members;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Member updatedMember){
        return false;
    }

    @Override
    public boolean delete(){
        return false;
    }

    @Override
    public Member create(){
        return null;
    }

}
