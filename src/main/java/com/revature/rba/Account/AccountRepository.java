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
    public Account findById(int routingNumber) {
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            Account account = new Account();
            String sql = "select * from accounts where routing_number=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, routingNumber);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                account = generateFromResult(rs);
            }

            return account;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
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

    public boolean deposit(Account account){
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "update accounts set amount=? where routing_number=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            double amount = amountAfterDeposit(account);

            ps.setDouble(1, amount);
            ps.setInt(2, account.getRoutingNumber());

            int executed = ps.executeUpdate();

            if(executed == 0){
                throw new RuntimeException("Unable to execute update");
            }

            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(Account account){
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "update accounts set amount=? where routing_number=?";

            if(canWithdraw(account) == -1){
                throw new InvalidInputException("There are not enough funds to withdraw that amount");
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, canWithdraw(account));
            ps.setInt(2, account.getRoutingNumber());

            int executed = ps.executeUpdate();

            if(executed == 0){
                throw new RuntimeException("Unable to perform action. Make sure routing information is correct");
            }

            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private double canWithdraw(Account account){
        double amount = findById(account.getRoutingNumber()).getAmount(); // actual amount in db

        if(account.getAmount() > amount){
            return -1;
        }

        return amount - account.getAmount();
    }

    private double amountAfterDeposit(Account account){
        double amount = findById(account.getRoutingNumber()).getAmount();

        return amount + account.getAmount();
    }

    public List<Account> findAccountsFor(int id){
        try(Connection conn = ConnectionFactory.getConnectionFactory().getConnection()){
            List<Account> account = new ArrayList<Account>();
            String sql = "select * from accounts where user_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                account.add(generateFromResult(rs));
            }

            return account;
        }catch (SQLException e){
            return null;
        }
    }
}
