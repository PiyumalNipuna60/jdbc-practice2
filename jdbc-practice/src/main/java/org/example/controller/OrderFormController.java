package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Tm.ItemTm;
import org.example.dto.CustomerDto;
import org.example.dto.ItemDto;
import org.example.dto.OrderDetailsDto;
import org.example.model.CustomerModel;
import org.example.model.ItemModel;
import org.example.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
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
    private ObservableList<OrderDetailsDto> observableList = FXCollections.observableArrayList();
    private double fullTotal=0;

    public void btnAddToCart(ActionEvent actionEvent) {
        String itemCode = String.valueOf(cmbCode.getValue());
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String description = txtDescription.getText();

        fullTotal += (unitPrice * qty);

        OrderDetailsDto orderDto = new OrderDetailsDto(itemCode, description, unitPrice, qty, (unitPrice * qty));
        observableList.add(orderDto);
        tblOrder.setItems(observableList);
        txtNetTotal.setText(String.valueOf(fullTotal));
    }

    public void btnPlaceOrder(ActionEvent actionEvent) throws SQLException {
        String orderId = txtOrderId.getText();
        String date = txtOrderDate.getText();
        String customerId = String.valueOf(cmbCustomerId.getValue());
        Double total = Double.valueOf(txtNetTotal.getText());

        OrderModel orderModel = new OrderModel();
        boolean b = orderModel.saveOrder(orderId, date, customerId, total, observableList);
        if (b){
            new Alert(Alert.AlertType.CONFIRMATION,"save Order..!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong..!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCustomerValues();
        setItemCOde();

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));


    }

    private void setItemCOde() {
        ItemModel itemModel = new ItemModel();
        ArrayList<ItemTm> all = itemModel.getAll();
        ArrayList<String> itemCode = new ArrayList<>();

        for (ItemTm tm : all) {
            itemCode.add(tm.getItemCode());
        }
        cmbCode.setItems(FXCollections.observableList(itemCode));
    }

    private void setCustomerValues() {
        CustomerModel customerModel = new CustomerModel();
        ArrayList<String> allId = customerModel.getAllId();
        cmbCustomerId.setItems(FXCollections.observableList(allId));
    }

    public void cmbCOdeOnAction(ActionEvent actionEvent) {
        String code = String.valueOf(cmbCode.getValue());
        ItemModel itemModel = new ItemModel();
        ItemDto itemDto = itemModel.searchItem(code);
        txtDescription.setText(itemDto.getDescription());
        txtUnitPrice.setText(String.valueOf(itemDto.getPrice()));
        txtQtyOnHand.setText(String.valueOf(itemDto.getQty()));
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        CustomerModel customerModel = new CustomerModel();
        CustomerDto customerDto = customerModel.searchCustomer(String.valueOf(cmbCustomerId.getValue()));
        txtCustomerName.setText(customerDto.getName());
    }

}
