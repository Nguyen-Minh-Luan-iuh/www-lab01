package com.example.www_lab01_tuan01.repositoies;

import com.example.www_lab01_tuan01.cfg.DBConnection;
import com.example.www_lab01_tuan01.models.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRep {
    private Connection connection;

    public RoleRep() throws SQLException, ClassNotFoundException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public Optional<Role> findRoleById(String id){
        String sql = "SELECT * FROM role where role_id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Role role = new Role();
                role.setRoleId(rs.getString("role_id"));
                role.setRoleName(rs.getString("role_name"));
                role.setDescription(rs.getString("description"));
                role.setStatus(rs.getInt("status"));

                return Optional.of(role);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Role> getAll(){
        List<Role>roles = new ArrayList<>();
        String sql = "SELECT * FROM role";
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Role role = new Role();
                role.setRoleId(rs.getString("role_id"));
                role.setStatus(rs.getInt("status"));
                role.setRoleName(rs.getString("role_name"));
                role.setDescription(rs.getString("description"));

                roles.add(role);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return roles;
    }

    public void addRole(Role role){
        String sql = "INSERT INTO role(role_id, role_name, description, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, role.getRoleId());
            stmt.setString(2, role.getRoleName());
            stmt.setString(3, role.getDescription());
            stmt.setInt(4, role.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
