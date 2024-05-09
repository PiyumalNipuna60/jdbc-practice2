package org.example.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.example.db.DBConnection;
import org.example.dto.CustomerDto;
import org.example.model.CustomerModel;
import org.example.util.ValidateUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    public ComboBox cmbId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        loadTableData();
        loadComboId();

        Pattern patternId = Pattern.compile("^(C0)[0-9]{1,5}$");
        Pattern patternName = Pattern.compile("^[A-z]{3,}$");  //[0-9 a-z]{10}
        Pattern patternAge = Pattern.compile("^[0-9]{1,3}$"); //[0-9 A-z / .]{3,} // ^[0-9]{10}$  //^(070 |071 | 072 | 076) [0-9] {7}$

        map.put(txtId, patternId);
        map.put(txtName, patternName);
        map.put(txtAge, patternAge);
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
        ValidateUtil.validation(map);
    }



    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String age = txtAge.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        CustomerModel customerModel = new CustomerModel();
        int i = customerModel.saveCustomer(new CustomerDto(id, name, age, address, contact));

        if (i > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "save Customer..!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
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


    private void loadComboId() {
        CustomerModel customerModel = new CustomerModel();
        ArrayList<String> allId = customerModel.getAllId();
        cmbId.setItems(FXCollections.observableList(allId));
    }

    private void loadTableData() {
        ArrayList<Object> kamal = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                CustomerDto customerDto = new CustomerDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
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

    public void searchOnKeyRelesed(KeyEvent keyEvent) {
        CustomerModel customerModel = new CustomerModel();
        CustomerDto customerDto = customerModel.searchCustomer(txtId.getText());

        txtName.setText(customerDto.getName());
        txtContact.setText(customerDto.getContact());
        txtAddress.setText(customerDto.getAddress());
        txtAge.setText(customerDto.getAge());
    }

    public void cmbOnAction(ActionEvent actionEvent) {
        Object value = cmbId.getValue();
        CustomerModel customerModel = new CustomerModel();
        CustomerDto customerDto = customerModel.searchCustomer(String.valueOf(value));
//        cmbId.setValue();
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/"));
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
