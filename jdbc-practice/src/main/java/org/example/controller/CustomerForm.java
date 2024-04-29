package org.example.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.db.DBConnection;
import org.example.dto.CustomerDto;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerForm implements Initializable {
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
                loadTableData();
                btnClearOnAction();
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
        String id = txtId.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from customer where id=?");
            pstm.setObject(1, id);

            int i = pstm.executeUpdate();
            if (i > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Delete Date..!").show();
                loadTableData();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                txtAge.setText(resultSet.getString(3));
                txtContact.setText(resultSet.getString(5));
                txtName.setText(resultSet.getString(2));
                txtAddress.setText(resultSet.getString(4));
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty data..!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction() {
        txtAge.clear();
        txtId.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        loadTableData();
    }

    private void loadTableData() {
        ArrayList<Object> kamal = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                CustomerDto customerDto = new CustomerDto(
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                kamal.add(customerDto);
            }
            tblCustomer.setItems(FXCollections.observableList(kamal));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
