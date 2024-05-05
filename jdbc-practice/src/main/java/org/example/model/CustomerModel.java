package org.example.model;

import javafx.scene.control.Alert;
import org.example.db.DBConnection;
import org.example.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerModel {
    public int saveCustomer(CustomerDto customerDto) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?,?,?)");
            pstm.setObject(1, customerDto.getId());
            pstm.setObject(2, customerDto.getName());
            pstm.setObject(3, customerDto.getAge());
            pstm.setObject(4, customerDto.getAddress());
            pstm.setObject(5, customerDto.getContact());

            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    public int saveCusromer(String id, String name, String age, String address, String contact) {
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?,?,?)");
//            pstm.setObject(1, id);
//            pstm.setObject(2, name);
//            pstm.setObject(3, age);
//            pstm.setObject(4, address);
//            pstm.setObject(5, contact);
//            return pstm.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
