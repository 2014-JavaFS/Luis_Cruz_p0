package com.revature.rba.Member;

/*
    Follows DAO pattern (Data Access Object)
 */

import com.revature.rba.util.ConnectionFactory;
import com.revature.rba.util.interfaces.Crudable;
import com.revature.rba.util.interfaces.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository implements Crudable<Member> {

    public MemberRepository(){

    }
    @Override
    public List<Member> findAll() {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            List<Member> members = new ArrayList<>();

            String sql = "select * from members";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()){
                members.add(generateFromResult(rs));
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


    public Member findUsingCredentials(String email, String password) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {
            Member member = new Member();

            String sql = "select * from members where email=? and password=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                member = generateFromResult(rs);
            }
            return member;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static Member generateFromResult(ResultSet rs) throws SQLException{
        Member member = new Member();

        member.setMemberId(rs.getInt("user_id"));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        member.setType(Member.MemberType.valueOf(rs.getString("member_type")));

        return member;
    }

}
