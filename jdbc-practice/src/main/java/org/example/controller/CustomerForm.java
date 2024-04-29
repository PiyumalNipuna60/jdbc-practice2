package org.example.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.db.DBConnection;
import org.example.model.CustomerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerForm {
    public TextField txtName;
    public TextField txtAge;
    public TextField txtAddress;
    public TextField txtContact;
    public TableView tblCustomer;
    public TableColumn colAge;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TextField txtId;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String age = txtAge.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?,?,?)");
            pstm.setObject(1, id);
            pstm.setObject(2, name);
            pstm.setObject(3, age);
            pstm.setObject(4, address);
            pstm.setObject(5, contact);
            int i = pstm.executeUpdate();

            if (i > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "save Customer..!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String age = txtAge.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        Connection connection = DBConnection.getInstance().getConnection();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }
}
