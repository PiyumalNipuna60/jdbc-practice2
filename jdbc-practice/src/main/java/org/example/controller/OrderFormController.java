package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.dto.CustomerDto;
import org.example.model.CustomerModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public Label lblNetTotal;
    public TextField txtNetTotal;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public ComboBox cmbCustomerId;
    public ComboBox cmbCode;
    public Label lbQtyOnHand;
    public Label lblUnitPrice;
    public Label lblQty;
    public TextField txtCustomerName;
    public TextField txtOrderDate;
    public Label lblCode;
    public Label lblDescription;
    public Label lblCustomerName;
    public Label lblCustomerId;
    public Label lblOrderDate;
    public Label lblOrderId;
    public TextField txtOrderId;
    public TableView tblOrder;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colAction;


    public void btnAddToCart(ActionEvent actionEvent) {
        // sout txtfeild data print
    }

    public void btnPlaceOrder(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCustomerValues();
    }

    private void setCustomerValues() {
        CustomerModel customerModel = new CustomerModel();
        ArrayList<String> allId = customerModel.getAllId();
        cmbCustomerId.setItems(FXCollections.observableList(allId));
    }

    public void cmbCOdeOnAction(ActionEvent actionEvent) {
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        CustomerModel customerModel = new CustomerModel();
        CustomerDto customerDto = customerModel.searchCustomer(String.valueOf(cmbCustomerId.getValue()));
        txtCustomerName.setText(customerDto.getName());
    }

}
