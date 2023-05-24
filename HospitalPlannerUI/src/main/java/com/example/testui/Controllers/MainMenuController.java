package com.example.testui.Controllers;

import com.example.testui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
    private TextField adress;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField email;

    @FXML
    private void initialize() {;
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

    public void submitButtonAction() {
        if (areToateCampurileCompletate()) {
            System.out.println("Submit Button Pressed");
            System.out.println("First Name: " + firstNameText.getText());
            System.out.println("Last Name: " + lastNameText.getText());
            System.out.println("Date of Birth: " + dateOfBirth.getValue());
            System.out.println("Address: " + adress.getText());
            System.out.println("Phone Number: " + phoneNumber.getText());
            System.out.println("Email: " + email.getText());
            sceneManager.showAppointments();
        } else {
            String errorMessage = "Please complete all red fields";
            if(firstNameText.getText().isEmpty()) {
                firstNameText.setStyle("-fx-border-color: red");
            }
            if(lastNameText.getText().isEmpty()) {
                lastNameText.setStyle("-fx-border-color: red");
            }
            if(phoneNumber.getText().isEmpty()) {
                phoneNumber.setStyle("-fx-border-color: red");
            }
            if(dateOfBirth.getValue() == null) {
                dateOfBirth.setStyle("-fx-border-color: red");
            }

            sceneManager.showPopUp(errorMessage);
        }
    }

    private boolean areToateCampurileCompletate() {
        return !firstNameText.getText().isEmpty()
                && !lastNameText.getText().isEmpty()
                && dateOfBirth.getValue() != null
                && !phoneNumber.getText().isEmpty();
    }

}
