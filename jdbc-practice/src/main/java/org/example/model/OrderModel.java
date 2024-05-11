package org.example.model;


import javafx.collections.ObservableList;
import org.example.db.DBConnection;
import org.example.dto.OrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderModel {
    public void saveOrder(String orderId, String date, String customerId, String total, ObservableList<OrderDto> observableList) throws SQLException {
        Connection connection=null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

        }catch (Exception e){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
    }
}

