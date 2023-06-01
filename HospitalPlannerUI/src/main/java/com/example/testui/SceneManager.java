package com.example.testui;

import com.example.testui.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class SceneManager {
    private Stage stage;

    public SceneManager(Stage stage){
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void showMainMenu(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setSceneManager(this);


            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void showPatientAppointments(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("patient-appointments.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            PatientAppointmentsController patientAppointmentsController = loader.getController();
            patientAppointmentsController.setSceneManager(this);


            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void showDoctorAppointments(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("doctor-appointments.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            DoctorAppointmentsController doctorAppointmentsController = loader.getController();
            doctorAppointmentsController.setSceneManager(this);


            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void showPatientData(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("patient-data.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            PatientDataController patientDataController = loader.getController();
            patientDataController.setSceneManager(this);


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

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public void showPopUp(String message) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.initOwner(stage);
        dialog.setTitle("Pop-up");

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(message);
        dialogPane.getButtonTypes().add(javafx.scene.control.ButtonType.OK);

        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }


}
