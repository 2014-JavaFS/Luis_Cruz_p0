package com.revature.rba.Account;

import com.revature.rba.util.ConnectionFactory;
import com.revature.rba.util.exceptions.InvalidInputException;
import com.revature.rba.util.interfaces.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements Repository<Account> {

    @Override
    public List<Account> findAll(){
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            List<Account> accounts = new ArrayList<>();
            String sql = "select * from accounts";

            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()){
                accounts.add(generateFromResult(rs));
            }

            return accounts;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account generateFromResult(ResultSet rs) throws SQLException {
        Account account = new Account();

        account.setRoutingNumber(rs.getInt("routing_number"));
        account.setMemberId(rs.getInt("user_id"));
        account.setAmount(rs.getDouble("amount"));
        account.setType(Account.AccountType.valueOf(rs.getString("account_type")));
        account.setAccountName(rs.getString("account_name"));

        return account;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    @Override
    public boolean update(Account updatedObject, int id) {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public Account create(Account account) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "insert into accounts values(?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, account.getRoutingNumber());
            ps.setInt(2, account.getMemberId());
            ps.setObject(3, account.getType(), Types.OTHER);
            ps.setString(4, account.getAccountName());
            ps.setDouble(5, account.getAmount());

            int executed = ps.executeUpdate();

            if(executed == 0){
                throw new InvalidInputException("Account not created. Make sure the values were set correctly.");
            }

            return account;

        } catch (SQLException | InvalidInputException e){
            e.printStackTrace();
            return null;
        }
    }
}
