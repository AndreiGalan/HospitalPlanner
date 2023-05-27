package com.example.testui.controllers;

import com.example.testui.SceneManager;
import com.example.testui.fetchControllers.PatientFetchController;
import com.example.testui.model.PatientEntity;
import com.example.testui.util.InfoUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.IllegalFormatCodePointException;
import java.util.Optional;

public class MainMenuController {

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private Button xButton;
    @FXML
    private Button submitButton;

    @FXML
    private AnchorPane topAnchorPane;

    @FXML
    private AnchorPane centerAnchorPane;

    @FXML
    private TextField firstNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField address;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField email;

    @FXML
    private Button dropButton;
    private PatientEntity patient;

    private PatientFetchController patientFetchController = new PatientFetchController();
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
            Optional.ofNullable(this.address).ifPresent(addressField -> addressField.setText(Optional.ofNullable(InfoUser.getAddress()).orElse("")));
            phoneNumber.setText(Optional.ofNullable(InfoUser.getPhoneNumber()).orElse(""));
            email.setText(Optional.ofNullable(InfoUser.getEmail()).orElse(""));

        }
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

    public void submitButtonAction() {
        if (areToateCampurileCompletate()) {

            initPatient();
            PatientEntity registredPatient = patientFetchController.registerPatient(this.patient);
            System.out.println(registredPatient);
            if(registredPatient != null) {
                InfoUser.setInfo(registredPatient);
                ;
                sceneManager.showAppointments();
            } else {
                sceneManager.showPopUp("Failed to register the patient.");
            }

        } else {
            String errorMessage = "Please complete all required fields";
            if (firstNameText.getText().isEmpty()) {
                firstNameText.setStyle("-fx-border-color: red");
            }
            if (lastNameText.getText().isEmpty()) {
                lastNameText.setStyle("-fx-border-color: red");
            }
            if (phoneNumber.getText().isEmpty()) {
                phoneNumber.setStyle("-fx-border-color: red");
            }
            if (dateOfBirth.getValue() == null) {
                dateOfBirth.setStyle("-fx-border-color: red");
            }

            sceneManager.showPopUp(errorMessage);
        }
    }

    //TODO : refactor this
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

        // Verificarea și setarea adresei
        if (this.address != null && !this.address.getText().isEmpty()) {
            this.patient.setAddress(this.address.getText());
        }

        // Verificarea și setarea datei de naștere
        if (this.dateOfBirth.getValue() != null) {
            this.patient.setDateOfBirth(this.dateOfBirth.getValue());
        }

        // Verificarea și setarea adresei de email
        if (this.email.getText().isEmpty()) {
            this.patient.setEmail(null);
        } else {
            this.patient.setEmail(this.email.getText());
        }

        // Verificarea și setarea numărului de telefon
        if (this.phoneNumber.getText().isEmpty()) {
            this.patient.setPhoneNumber(null);
        } else {
            this.patient.setPhoneNumber(this.phoneNumber.getText());
        }
    }


    private boolean areToateCampurileCompletate() {
        return !firstNameText.getText().isEmpty()
                && !lastNameText.getText().isEmpty()
                && dateOfBirth.getValue() != null
                && !phoneNumber.getText().isEmpty();
    }
}
