package com.example.testui.Controllers;

import com.example.testui.SceneManager;
import com.example.testui.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class ViewAppointmentsController {
    private SceneManager sceneManager;

    @FXML
    private ListView<Appointment> appointmentsListView;

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        fetchAppointments();
        appointmentsListView.setItems(appointments);

    }

    private void fetchAppointments() {
        //TODO fetch appointments from API

        appointments.add(new Appointment());
        appointments.add(new Appointment());
        appointments.add(new Appointment());
        appointments.add(new Appointment());
    }

    @FXML
    private void initialize() {;
        topAnchorPane.setOnMousePressed(e -> topAnchorPane.setOnMouseDragged(e2 -> {
            Stage stage = (Stage) topAnchorPane.getScene().getWindow();
            stage.setX(e2.getScreenX() - e.getSceneX());
            stage.setY(e2.getScreenY() - e.getSceneY());
        }));
    }

    @FXML
    private Button xButton;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane topAnchorPane;
    public void xButtonAction() {
        System.out.println("X Button Pressed");
        Stage stage = (Stage) xButton.getScene().getWindow();
        stage.close();
    }

    public void backButtonAction() {
        System.out.println("Back Button Pressed");
        sceneManager.showMainMenu();
    }


}
