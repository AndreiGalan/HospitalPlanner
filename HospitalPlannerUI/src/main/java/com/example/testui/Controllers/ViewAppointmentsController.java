package com.example.testui.Controllers;

import com.example.testui.SceneManager;
import com.example.testui.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class ViewAppointmentsController{
    private SceneManager sceneManager;

    @FXML
    private ListView<Appointment> appointmentsListView;

    @FXML
    private ChoiceBox<String> appointmentTypeChoiceBox;

    @FXML
    private DatePicker dateApp1;

    @FXML
    private DatePicker dateApp2;

    @FXML
    private DatePicker dateApp3;

    @FXML
    private Spinner<Integer> startH1;

    @FXML
    private Spinner<Integer> startM1;

    @FXML
    private Spinner<Integer> startH2;

    @FXML
    private Spinner<Integer> startM2;

    @FXML
    private Spinner<Integer> startH3;

    @FXML
    private Spinner<Integer> startM3;

    @FXML
    private Spinner<Integer> endH1;

    @FXML
    private Spinner<Integer> endM1;

    @FXML
    private Spinner<Integer> endH2;

    @FXML
    private Spinner<Integer> endM2;

    @FXML
    private Spinner<Integer> endH3;

    @FXML
    private Spinner<Integer> endM3;

    private String[] appointmentTypes = {"Consultation", "Surgery", "Therapy", "Vaccination"};

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

        appointmentTypeChoiceBox.getItems().addAll(appointmentTypes);

        initializeSpinners();
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> valueFactoryHours1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        startH1.setValueFactory(valueFactoryHours1);

        SpinnerValueFactory<Integer> valueFactoryHours2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        startH2.setValueFactory(valueFactoryHours2);

        SpinnerValueFactory<Integer> valueFactoryHours3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        startH3.setValueFactory(valueFactoryHours3);

        SpinnerValueFactory<Integer> valueFactoryHours4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        endH1.setValueFactory(valueFactoryHours4);

        SpinnerValueFactory<Integer> valueFactoryHours5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        endH2.setValueFactory(valueFactoryHours5);

        SpinnerValueFactory<Integer> valueFactoryHours6 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        endH3.setValueFactory(valueFactoryHours6);

        SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        startM1.setValueFactory(valueFactoryMinutes1);

        SpinnerValueFactory<Integer> valueFactoryMinutes2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        startM2.setValueFactory(valueFactoryMinutes2);

        SpinnerValueFactory<Integer> valueFactoryMinutes3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        startM3.setValueFactory(valueFactoryMinutes3);

        SpinnerValueFactory<Integer> valueFactoryMinutes4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        endM1.setValueFactory(valueFactoryMinutes4);

        SpinnerValueFactory<Integer> valueFactoryMinutes5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        endM2.setValueFactory(valueFactoryMinutes5);

        SpinnerValueFactory<Integer> valueFactoryMinutes6 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        endM3.setValueFactory(valueFactoryMinutes6);
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
