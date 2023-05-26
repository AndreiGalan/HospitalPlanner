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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ViewAppointmentsController{
    private SceneManager sceneManager;

    @FXML
    private ListView<Appointment> appointmentsListView;

    @FXML
    private ChoiceBox<String> appointmentTypeChoiceBox;

    @FXML
    private Button dropButton;

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

        dateApp1.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        dateApp2.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        dateApp3.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

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

    public void dateApp1Action(){
        int startHour = 0;
        int startMinute = 0;
        if(dateApp1 != null)
            if(dateApp1.getValue().equals(LocalDate.now())){
                startHour = LocalTime.now().getHour();
                startMinute = LocalTime.now().getMinute();
            }

        SpinnerValueFactory<Integer> valueFactoryHours1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHour, 23, 0);
        startH1.setValueFactory(valueFactoryHours1);

        SpinnerValueFactory<Integer> valueFactoryHours4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHour, 23, 0);
        endH1.setValueFactory(valueFactoryHours4);

        SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startMinute, 59, 0);
        startM1.setValueFactory(valueFactoryMinutes1);

        SpinnerValueFactory<Integer> valueFactoryMinutes4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startMinute, 59, 0);
        endM1.setValueFactory(valueFactoryMinutes4);
    }

    public void dateApp2Action(){
        int startHour = 0;
        int startMinute = 0;
        if(dateApp2 != null)
            if(dateApp2.getValue().isEqual(LocalDate.now())) {
                LocalTime currentTime = LocalTime.now();
                startHour = currentTime.getHour();
                startMinute = currentTime.getMinute();
            }

        SpinnerValueFactory<Integer> valueFactoryHours1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHour, 23, 0);
        startH2.setValueFactory(valueFactoryHours1);

        SpinnerValueFactory<Integer> valueFactoryHours4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHour, 23, 0);
        endH2.setValueFactory(valueFactoryHours4);

        SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startMinute, 59, 0);
        startM2.setValueFactory(valueFactoryMinutes1);

        SpinnerValueFactory<Integer> valueFactoryMinutes4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startMinute, 59, 0);
        endM2.setValueFactory(valueFactoryMinutes4);
    }

    public void dateApp3Action(){
        int startHour = 0;
        int startMinute = 0;
        if(dateApp3 != null)
            if(dateApp3.getValue().isEqual(LocalDate.now())) {
                LocalTime currentTime = LocalTime.now();
                startHour = currentTime.getHour();
                startMinute = currentTime.getMinute();
            }

        SpinnerValueFactory<Integer> valueFactoryHours1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHour, 23, 0);
        startH3.setValueFactory(valueFactoryHours1);

        SpinnerValueFactory<Integer> valueFactoryHours4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHour, 23, 0);
        endH3.setValueFactory(valueFactoryHours4);

        SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startMinute, 59, 0);
        startM3.setValueFactory(valueFactoryMinutes1);

        SpinnerValueFactory<Integer> valueFactoryMinutes4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startMinute, 59, 0);
        endM3.setValueFactory(valueFactoryMinutes4);
    }

    public void startH1Action(){
        LocalTime currentTime = LocalTime.now();
        if(endH1.getValue() < startH1.getValue()) {
            SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(startH1.getValue(), 23, 0);
            endH1.setValueFactory(valueFactoryHour);
        }

        if(dateApp1.getValue() != null){
            System.out.println(dateApp1.getValue());
            if(dateApp1.getValue().isEqual(LocalDate.now())) {
                if (startH1.getValue() > currentTime.getHour()) {
                    SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    startM1.setValueFactory(valueFactoryMinutes1);
                    SpinnerValueFactory<Integer> valueFactoryMinutes2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    endM1.setValueFactory(valueFactoryMinutes2);
                }
            }
        }
    }

    public void startH2Action(){
        LocalTime currentTime = LocalTime.now();
        if(endH2.getValue() < startH2.getValue()) {
            SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(startH2.getValue(), 23, 0);
            endH2.setValueFactory(valueFactoryHour);
        }

        if(dateApp2.getValue() != null)
            if(dateApp2.getValue().isEqual(LocalDate.now())) {
                if (startH2.getValue() > currentTime.getHour()) {
                    SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    startM2.setValueFactory(valueFactoryMinutes1);
                    SpinnerValueFactory<Integer> valueFactoryMinutes2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    endM2.setValueFactory(valueFactoryMinutes2);
                }
            }
    }

    public void startH3Action(){
        LocalTime currentTime = LocalTime.now();
        if(endH3.getValue() < startH3.getValue()) {
            SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(startH3.getValue(), 23, 0);
            endH3.setValueFactory(valueFactoryHour);
        }

        if(dateApp3.getValue() != null)
            if(dateApp3.getValue().isEqual(LocalDate.now())) {
                if (startH3.getValue() > currentTime.getHour()) {
                    SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    startM3.setValueFactory(valueFactoryMinutes1);
                    SpinnerValueFactory<Integer> valueFactoryMinutes2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    endM3.setValueFactory(valueFactoryMinutes2);
                }
            }
    }

    public void endH1Action(){
        LocalTime currentTime = LocalTime.now();
        if(dateApp1.getValue() != null)
            if(dateApp1.getValue().isEqual(LocalDate.now())) {
                if (endH1.getValue() > currentTime.getHour()) {
                    SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    endM1.setValueFactory(valueFactoryMinutes1);
                }
            }
    }

    public void endH2Action(){
        LocalTime currentTime = LocalTime.now();
        if(dateApp2 != null)
            if(dateApp2.getValue().isEqual(LocalDate.now())) {
                if (endH2.getValue() > currentTime.getHour()) {
                    SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    endM2.setValueFactory(valueFactoryMinutes1);
                }
            }
    }

    public void endH3Action(){
        LocalTime currentTime = LocalTime.now();
        if(dateApp3 != null)
            if(dateApp3.getValue().isEqual(LocalDate.now())) {
                if (endH3.getValue() > currentTime.getHour()) {
                    SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
                    endM3.setValueFactory(valueFactoryMinutes1);
                }
            }
    }

    public void startM1Action(){
        if(endH1.getValue().equals(startH1.getValue())){
            if(endM1.getValue() < startM1.getValue()){
                SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startM1.getValue(), 59, 0);
                endM1.setValueFactory(valueFactoryMinutes1);
            }
        }
    }

    public void startM2Action(){
        if(endH2.getValue().equals(startH2.getValue())){
            if(endM2.getValue() < startM2.getValue()){
                SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startM2.getValue(), 59, 0);
                endM2.setValueFactory(valueFactoryMinutes1);
            }
        }
    }

    public void startM3Action(){
        if(endH3.getValue().equals(startH3.getValue())){
            if(endM3.getValue() < startM3.getValue()){
                SpinnerValueFactory<Integer> valueFactoryMinutes1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(startM3.getValue(), 59, 0);
                endM3.setValueFactory(valueFactoryMinutes1);
            }
        }
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

    public void dropButtonAction() {
        //minimize window
        System.out.println("Drop Button Pressed");
        Stage stage = (Stage) dropButton.getScene().getWindow();
        stage.setIconified(true);

    }

    public void backButtonAction() {
        System.out.println("Back Button Pressed");
        sceneManager.showMainMenu();
    }

    public void submitButtonAction(){
        if (areToateCampurileCompletate()) {
            sceneManager.showMainMenu();
        } else {
            String errorMessage = "Please complete all required fields";
            if(dateApp1.getValue() ==  null) {
                errorMessage += "\n- Date";
                dateApp1.setStyle("-fx-border-color: red");
            }

            if(appointmentsListView.getSelectionModel().getSelectedItem() == null) {
                errorMessage += "\n- Doctor";
                appointmentsListView.setStyle("-fx-border-color: red");
            }

            if(appointmentTypeChoiceBox.getValue() == null) {
                errorMessage += "\n- Appointment Type";
                appointmentTypeChoiceBox.setStyle("-fx-border-color: red");
            }

            sceneManager.showPopUp(errorMessage);
        }
    }

    private boolean areToateCampurileCompletate() {
        if(dateApp1.getValue() ==  null)
            return false;

        if(appointmentsListView.getSelectionModel().getSelectedItem() == null)
            return false;

        if(appointmentTypeChoiceBox.getValue() == null)
            return false;

        return true;
    }

}
