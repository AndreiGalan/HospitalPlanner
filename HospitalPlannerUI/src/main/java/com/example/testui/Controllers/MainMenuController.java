package com.example.testui.Controllers;

import com.example.testui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

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
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
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
            sceneManager.showAppointments();
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

    private boolean areToateCampurileCompletate() {
        return !firstNameText.getText().isEmpty()
                && !lastNameText.getText().isEmpty()
                && dateOfBirth.getValue() != null
                && !phoneNumber.getText().isEmpty();
    }
}
