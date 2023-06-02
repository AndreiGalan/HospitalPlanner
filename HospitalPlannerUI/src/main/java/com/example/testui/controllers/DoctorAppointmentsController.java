package com.example.testui.controllers;

import com.example.testui.SceneManager;
import com.example.testui.fetchControllers.AppointmentFetchController;
import com.example.testui.fetchControllers.DoctorFetchController;
import com.example.testui.model.Appointment;
import com.example.testui.model.DoctorEntity;
import com.example.testui.model.TimeInterval;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class DoctorAppointmentsController {
    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private Button xButton;

    @FXML
    private Button dropButton;

    @FXML
    private AnchorPane topAnchorPane;

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<DoctorEntity> doctorList;

    @FXML
    private DatePicker dateAppointment;

    @FXML
    private ListView<TimeInterval> doctorTimetable;

    private ObservableList<DoctorEntity> doctors = FXCollections.observableArrayList();

    private static DoctorFetchController doctorFetchController = new DoctorFetchController();

    private AppointmentFetchController appointmentFetchController = new AppointmentFetchController();

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchDoctorButton;

    @FXML
    private Button resetButton;


    @FXML
    private void initialize() {

        topAnchorPane.setOnMousePressed(e -> topAnchorPane.setOnMouseDragged(e2 -> {
            Stage stage = (Stage) topAnchorPane.getScene().getWindow();
            stage.setX(e2.getScreenX() - e.getSceneX());
            stage.setY(e2.getScreenY() - e.getSceneY());
        }));

        filterChoiceBox.getItems().addAll(doctorFetchController.getAllSpecializations());

        initializeDoctors();
    }

    public void xButtonAction() {
        Stage stage = (Stage) xButton.getScene().getWindow();
        stage.close();
    }

    public void dropButtonAction() {
        Stage stage = (Stage) dropButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void backButtonAction() {
        sceneManager.showMainMenu();
    }

    public void searchButtonAction() {
        if (checkDoctorList()) {
            doctorList.setStyle("-fx-border-color: none; -fx-border-width: 0px;");
            dateAppointment.setStyle("-fx-border-color: none; -fx-border-width: 0px;");
            doctorTimetable.getItems().clear();

            List<TimeInterval> appointments = appointmentFetchController.getDoctorAppointments(doctorList.getSelectionModel().getSelectedItem().getId(), dateAppointment.getValue());
            if(!appointments.isEmpty())
                doctorTimetable.getItems().addAll(appointments);
            else
                sceneManager.showPopUp("No appointments found!");
        }
        else {
            sceneManager.showPopUp("Please complete all fields!");
            if(doctorList.getSelectionModel().getSelectedItem() == null) {
                doctorList.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            else
                doctorList.setStyle("-fx-border-color: none; -fx-border-width: 0px;");

            if(dateAppointment.getValue() == null) {
                dateAppointment.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            else
                dateAppointment.setStyle("-fx-border-color: none; -fx-border-width: 0px;");
        }

    }


    public boolean checkDoctorList() {
        return doctorList.getSelectionModel().getSelectedItem() != null && dateAppointment.getValue() != null;
    }

    private void initializeDoctors() {
        doctors.addAll(doctorFetchController.getDoctors());
        doctorList.setItems(doctors);
    }

    public void filterChoiceBoxAction() {
        if(doctorFetchController.getDoctorsByFilter(searchField.getText(), filterChoiceBox.getValue()).isEmpty())
            sceneManager.showPopUp("No doctor found!");
        else {
            doctorList.getItems().clear();
            doctors.addAll(doctorFetchController.getDoctorsByFilter(searchField.getText(), filterChoiceBox.getValue()));
            doctorList.setItems(doctors);
        }
    }

    public void searchDoctorButtonAction(){
        if(!searchField.getText().isEmpty()){
            searchField.setStyle("-fx-border-color: none; -fx-border-width: none;");
            if(doctorFetchController.getDoctorsByFilter(searchField.getText(), filterChoiceBox.getValue()).isEmpty())
                sceneManager.showPopUp("No doctor found!");
            else {
                doctorList.getItems().clear();
                doctors.addAll(doctorFetchController.getDoctorsByFilter(searchField.getText(), filterChoiceBox.getValue()));
                doctorList.setItems(doctors);
            }

        }
        else {
            sceneManager.showPopUp("Please enter a name!");
            searchField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }

    public void resetButtonAction(){
        doctorList.getItems().clear();
        doctors.addAll(doctorFetchController.getDoctors());
        doctorList.setItems(doctors);
        searchField.clear();
        searchField.setStyle("-fx-border-color: none; -fx-border-width: none;");
        filterChoiceBox.setValue(null);
    }


}
