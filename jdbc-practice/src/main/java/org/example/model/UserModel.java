package org.example.model;

import org.example.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public boolean userCheck(String userName, String password){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from user where userName=? && password=?");
            pstm.setObject(1,userName);
            pstm.setObject(2, password);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
