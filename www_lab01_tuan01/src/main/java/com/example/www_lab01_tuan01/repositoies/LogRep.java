package com.example.www_lab01_tuan01.repositoies;

import com.example.www_lab01_tuan01.cfg.DBConnection;
import com.example.www_lab01_tuan01.models.Log;

import java.sql.*;
import java.util.Optional;

public class LogRep {
    private Connection connection;

    public LogRep() throws SQLException, ClassNotFoundException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public void insertLog(Log log){
        String sql = "INSERT INTO log (id, account_id,  notes) VALUES (?,  ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setLong(1, log.getId());
            stmt.setString(2, log.getAccountId());
//            stmt.setDate(3, new Date(log.getLoginTime().getTime()));
            stmt.setString(3, log.getNotes());

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLogoutTime(long logId){
        String sql = "UPDATE log set logout_time = ? where id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(2, logId);

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<Log> getLogById(long logId){
        String sql = "SELECT * FROM log where id = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setLong(1, logId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Log log = new Log();
                log.setId(logId);
                log.setAccountId(rs.getString("account_id"));
                log.setLoginTime(rs.getTimestamp("login_time"));
                log.setLogoutTime(rs.getTimestamp("logout_time"));
                log.setNotes(rs.getString("notes"));

                return Optional.of(log);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }
}
