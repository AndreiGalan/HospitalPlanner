package com.example.testui;

import com.example.testui.Controllers.MainMenuController;
import com.example.testui.Controllers.ViewAppointmentsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneManager {
    private Stage stage;

    public SceneManager(Stage stage){
        this.stage = stage;
    }

    public void showMainMenu(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setSceneManager(this);

            stage.setTitle("Hello!");
            //stage.initStyle(StageStyle.UNDECORATED); // EXCEPTION : java.lang.IllegalStateException: Cannot set style once stage has been set visible
            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void showAppointments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-appointments.fxml"));
            Parent root = loader.load();
            ViewAppointmentsController viewAppointmentsController = loader.getController();
            viewAppointmentsController.setSceneManager(this);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            stage.setTitle("View Appointments");
            //stage.initStyle(StageStyle.UNDECORATED); // EXCEPTION : java.lang.IllegalStateException: Cannot set style once stage has been set visible
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }
}
