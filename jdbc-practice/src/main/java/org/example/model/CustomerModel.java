package org.example.model;

import javafx.scene.control.Alert;
import org.example.db.DBConnection;
import org.example.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public ArrayList<String> getAllId() {
        ArrayList<String> allId = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select id from customer");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()){
                allId.add(String.valueOf(resultSet.getString(1)));
            }
            return allId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerDto searchCustomer(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer where id=?");
            pstm.setObject(1,id);

            ResultSet resultSet = pstm.executeQuery();
            CustomerDto customerDto = new CustomerDto();
            if (resultSet.next()){
              customerDto.setId(resultSet.getString(1));
              customerDto.setName(resultSet.getString(2));
              customerDto.setAge(resultSet.getString(3));
              customerDto.setAddress(resultSet.getString(4));
              customerDto.setContact(resultSet.getString(5));
            }
            return customerDto;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
