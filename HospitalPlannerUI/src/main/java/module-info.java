module com.example.testui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testui to javafx.fxml;
    opens com.example.testui.Controllers to javafx.fxml;
    exports com.example.testui;
    exports com.example.testui.Controllers;
}