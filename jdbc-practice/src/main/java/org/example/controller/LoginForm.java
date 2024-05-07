package org.example.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.model.UserModel;
import org.example.navigation.Navigation;

public class LoginForm {
    public TextField txtUserName;
    public TextField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String password = txtPassword.getText();
        String userName = txtUserName.getText();

        UserModel userModel = new UserModel();
        boolean value = userModel.userCheck(userName, password);
        if (value==true){
            Navigation navigation = new Navigation();
            navigation.popUpNavigation("DashBoard.fxml");
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Wrong Details..!").show();
        }

    }
}
