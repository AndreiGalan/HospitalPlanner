package com.example.testui.controllers;

import com.example.testui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController {
    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private Button xButton;

    @FXML
    private Button dropButton;

    @FXML
    private Button viewDoctorTimetable;

    @FXML
    private Button viewYourAppointments;

    @FXML
    private Button makeAppointment;

    @FXML
    private AnchorPane topAnchorPane;

    @FXML
    private void initialize() {

        topAnchorPane.setOnMousePressed(e -> topAnchorPane.setOnMouseDragged(e2 -> {
            Stage stage = (Stage) topAnchorPane.getScene().getWindow();
            stage.setX(e2.getScreenX() - e.getSceneX());
            stage.setY(e2.getScreenY() - e.getSceneY());
        }));
    }

    public void xButtonAction() {
        System.out.println("X Button Pressed");
        Stage stage = (Stage) xButton.getScene().getWindow();
        stage.close();
    }

    public void dropButtonAction() {
        //minimize window
        System.out.println("Drop Button Pressed");
        Stage stage = (Stage) dropButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void viewDoctorTimetableAction(){
        sceneManager.showDoctorAppointments();
    }

    public void viewYourAppointmentsAction(){
        sceneManager.showPatientAppointments();
    }

    public void makeAppointmentAction(){
        sceneManager.showPatientData();
    }

}
