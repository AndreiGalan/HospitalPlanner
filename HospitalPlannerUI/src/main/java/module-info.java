module com.example.testui {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires gson;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens com.example.testui to javafx.fxml;
    opens com.example.testui.controllers to javafx.fxml;
    exports com.example.testui;
    exports com.example.testui.controllers;
    exports com.example.testui.model;
    exports com.example.testui.util;
    opens com.example.testui.util to javafx.fxml;
    exports com.example.testui.fetchControllers;
    opens com.example.testui.fetchControllers to javafx.fxml;
}