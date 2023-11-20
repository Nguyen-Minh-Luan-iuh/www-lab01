package com.example.www_lab01_tuan01.repositoies;

import com.example.www_lab01_tuan01.cfg.DBConnection;
import com.example.www_lab01_tuan01.models.Account;
import com.example.www_lab01_tuan01.models.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRep {
    private Connection connection;

    public AccountRep() throws SQLException, ClassNotFoundException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    private List<Role> grantAccesses(String accountId){
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT r.role_id, r.role_name, r.description, r.status FROM grant_access g JOIN role r ON g.role_id = r.role_id WHERE g.account_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Role role = new Role();
                role.setRoleId(rs.getString("role_id"));
                role.setDescription(rs.getString("description"));
                role.setRoleName(rs.getString("role_name"));
                role.setStatus(rs.getInt("status"));
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roles;
    }

    public List<Account> getAll(){
        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM account";
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Account account = new Account();
                account.setAccountId(rs.getString("account_id"));
                account.setFullName(rs.getString("full_name"));
                account.setPassword(rs.getString("password"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                account.setStatus(rs.getInt("status"));
                List<Role> roles = this.grantAccesses(account.getAccountId());
                account.setRoles(roles);
                accounts.add(account);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accounts;
    }

    public boolean addAccount(Account account){
        String sql = "INSERT INTO account(account_id, full_name, password, email, phone, status) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, account.getAccountId());
            stmt.setString(2, account.getFullName());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getEmail());
            stmt.setString(5, account.getPhone());
            stmt.setInt(6, 1);

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public Optional<Account> findByEmailAndPassword(String email, String password){
        String sql = "SELECT * FROM account where email = ? and password = ?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Account account = new Account();
                account.setAccountId(rs.getString("account_id"));
                account.setFullName(rs.getString("full_name"));
                account.setPassword(rs.getString("password"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                account.setStatus(rs.getInt("status"));

                return Optional.of(account);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Account>findById(String accountId){
        String sql = "SELECT * FROM account where account_id = ?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Account account = new Account();
                account.setAccountId(rs.getString("account_id"));
                account.setFullName(rs.getString("full_name"));
                account.setPassword(rs.getString("password"));
                account.setEmail(rs.getString("email"));
                account.setPhone(rs.getString("phone"));
                account.setStatus(rs.getInt("status"));

                return Optional.of(account);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public void deleteAccount(String accountId){
        String sql = "DELETE FROM account where account_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, accountId);

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateAccount(Account account){
        String sql = "UPDATE ACCOUNT SET full_name = ?, password = ?, email = ?, phone = ? where account_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, account.getFullName());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getEmail());
            stmt.setString(4, account.getPhone());
            stmt.setString(5, account.getAccountId());

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
