package com.revature.rba.util.auth;

import com.revature.rba.Account.Account;
import com.revature.rba.Member.Member;
import com.revature.rba.Member.MemberRepository;
import com.revature.rba.util.ConnectionFactory;
import com.revature.rba.util.interfaces.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthRepository implements Repository<Member> {

    public Member login(String email, String pass){
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            Member member = new Member();
            String sql = "select * from members where email=? and password=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                member = generateFromResult(rs);
            }
            return member;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Member generateFromResult(ResultSet rs) throws SQLException {
        Member member = new Member();

        member.setMemberId(rs.getInt("user_id"));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        member.setType(Member.MemberType.valueOf(rs.getString("member_type")));

        return member;
    }

    @Override
    public boolean update(Member updatedObject) {
        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "";

            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public Member create(Member O) {
        return null;
    }

    @Override
    public Member findById(int id){
        return null;
    }
}
