package org.example.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.dto.CustomerDto;

import java.io.IOException;
import java.net.URL;

public class Navigation {
    public void popUpNavigation(String path){
        try {
            URL resource = Navigation.class.getResource("/view/"+path);
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchNavigation(String path){

//        try {
//            URL resource = Navigation.class.getResource("/view/" + path);
//            Parent load = FXMLLoader.load(resource);
//            Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}
