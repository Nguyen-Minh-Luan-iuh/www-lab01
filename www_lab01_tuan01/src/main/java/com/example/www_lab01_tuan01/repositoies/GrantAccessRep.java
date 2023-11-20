package com.example.www_lab01_tuan01.repositoies;

import com.example.www_lab01_tuan01.cfg.DBConnection;
import com.example.www_lab01_tuan01.models.GrantAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrantAccessRep {
    private Connection connection;

    public GrantAccessRep() throws SQLException, ClassNotFoundException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public void addGrantAccess(GrantAccess grantAccess){
        String sql = "INSERT INTO grant_access(account_id, role_id, is_grant, note) values(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, grantAccess.getAccountId());
            stmt.setString(2, grantAccess.getRoleId());
            stmt.setInt(3, grantAccess.getIsGrant());
            stmt.setString(4, grantAccess.getNote());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GrantAccess> getGrantAccessByAccountId(String accountId){
        List<GrantAccess> grantAccesses = new ArrayList<>();
        String sql = "SELECT * FROM grant_access where account_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                GrantAccess grantAccess = new GrantAccess();
                grantAccess.setAccountId(rs.getString("account_id"));
                grantAccess.setRoleId(rs.getString("role_id"));
                grantAccess.setIsGrant(rs.getInt("is_grant"));
                grantAccess.setNote(rs.getString("note"));

                grantAccesses.add(grantAccess);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return grantAccesses;
    }
}
