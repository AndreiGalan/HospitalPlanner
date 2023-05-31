package com.example.testui.controllers;

import com.example.testui.SceneManager;
import com.example.testui.fetchControllers.AppointmentFetchController;
import com.example.testui.fetchControllers.PatientFetchController;
import com.example.testui.model.Appointment;
import com.example.testui.model.DoctorEntity;
import com.example.testui.model.PatientEntity;
import com.example.testui.util.InfoUser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PatientAppointmentsController {
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
    private TextField firstNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private ListView<Appointment> patientAppointments;

    private PatientEntity patient;

    private PatientFetchController patientFetchController = new PatientFetchController();

    private List<Appointment> appointments;

    private AppointmentFetchController appointmentFetchController = new AppointmentFetchController();

    @FXML
    private void initialize() {

        topAnchorPane.setOnMousePressed(e -> topAnchorPane.setOnMouseDragged(e2 -> {
            Stage stage = (Stage) topAnchorPane.getScene().getWindow();
            stage.setX(e2.getScreenX() - e.getSceneX());
            stage.setY(e2.getScreenY() - e.getSceneY());
        }));

        dateOfBirth.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        if(InfoUser.isCreated()) {
            firstNameText.setText(Optional.ofNullable(InfoUser.getFirstName()).orElse(""));
            lastNameText.setText(Optional.ofNullable(InfoUser.getLastName()).orElse(""));
            dateOfBirth.setValue(Optional.ofNullable(InfoUser.getDateOfBirth()).orElse(LocalDate.now()));

        }
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
        if (checkFields()) {
            firstNameText.setStyle("-fx-border-color: none; -fx-border-width: 0px;");
            lastNameText.setStyle("-fx-border-color: none; -fx-border-width: 0px;");
            dateOfBirth.setStyle("-fx-border-color: none; -fx-border-width: 0px;");

            initPatient();
            PatientEntity registredPatient = patientFetchController.registerPatient(this.patient);
            if(registredPatient != null) {
                InfoUser.setInfo(registredPatient);

                Long id = registredPatient.getId();


                appointments = appointmentFetchController.getPatientAppointments(id);

                if(appointments != null)
                    if(appointments.size() > 0)
                        ListAppointments(appointments);
                    else
                        sceneManager.showPopUp("No appointments found.");
                else
                    sceneManager.showPopUp("Failed to get the appointments.");

            } else {
                sceneManager.showPopUp("Failed to register the patient.");
            }
        }
        else {
            sceneManager.showPopUp("Please fill in all the fields!");
            if(firstNameText.getText().isEmpty()) {
                firstNameText.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            else
                firstNameText.setStyle("-fx-border-color: none; -fx-border-width: 0px;");

            if(lastNameText.getText().isEmpty()) {
                lastNameText.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            else
                lastNameText.setStyle("-fx-border-color: none; -fx-border-width: 0px;");

            if(dateOfBirth.getValue() == null) {
                dateOfBirth.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }
            else
                dateOfBirth.setStyle("-fx-border-color: none; -fx-border-width: 0px;");
        }

    }

    private void ListAppointments(List<Appointment> appointments) {
        patientAppointments.getItems().clear();
        patientAppointments.getItems().addAll(appointments);
    }

    public boolean checkFields() {
        if (firstNameText.getText().isEmpty() || lastNameText.getText().isEmpty() || dateOfBirth.getValue() == null) {
            return false;
        }
        return true;
    }

    private void initPatient() {
        this.patient = new PatientEntity();


        if (this.firstNameText.getText().isEmpty()) {
            this.patient.setFirstName(null);
        } else {
            this.patient.setFirstName(this.firstNameText.getText());
        }

        if (this.lastNameText.getText().isEmpty()) {
            this.patient.setLastName(null);
        } else {
            this.patient.setLastName(this.lastNameText.getText());
        }

        if (this.dateOfBirth.getValue() == null) {
            this.patient.setDateOfBirth(null);
        } else {
            this.patient.setDateOfBirth(this.dateOfBirth.getValue());
        }
    }
}
