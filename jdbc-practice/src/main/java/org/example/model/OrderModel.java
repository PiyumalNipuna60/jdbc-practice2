package org.example.model;


import javafx.collections.ObservableList;
import org.example.db.DBConnection;
import org.example.dto.ItemDto;
import org.example.dto.OrderDetailsDto;
import org.example.dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderModel {
    public boolean saveOrder(String orderId, String date, String customerId, Double total, ObservableList<OrderDetailsDto> observableList) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean saveOrder = save(new OrderDto(orderId, customerId, date, total));
            if (saveOrder == true) {

                boolean saveOrderDetails = orderDetailsSave(orderId, observableList);
                if (saveOrderDetails == true) {
                    boolean b = updateItemQty(observableList);
                    if (b == true) {
                        connection.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private boolean updateItemQty(ObservableList<OrderDetailsDto> observableList) throws SQLException {
        for (OrderDetailsDto dto : observableList) {
            ItemModel itemModel = new ItemModel();
            ItemDto itemDto = itemModel.searchItem(dto.getItemCode());
            boolean b = itemModel.updateItem(new ItemDto(dto.getItemCode(), dto.getDescription(), dto.getPrice(), itemDto.getQty()-dto.getQty()));
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private boolean orderDetailsSave(String orderId, ObservableList<OrderDetailsDto> observableList) throws SQLException {
        for (OrderDetailsDto dto : observableList) {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into OrderDetail values(?,?,?,?)");
            pstm.setObject(1, orderId);
            pstm.setObject(2, dto.getItemCode());
            pstm.setObject(3, dto.getQty());
            pstm.setObject(4, dto.getPrice());
            boolean b = pstm.executeUpdate() > 0;
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private boolean save(OrderDto orderDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("insert into orders values(?,?,?,?)");
        pstm.setObject(1, orderDto.getOrderId());
        pstm.setObject(2, orderDto.getCustomerId());
        pstm.setObject(3, orderDto.getDate());
        pstm.setObject(4, orderDto.getPrice());
        return pstm.executeUpdate() > 0;
    }


}

