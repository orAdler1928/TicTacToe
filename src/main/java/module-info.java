module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.model to javafx.base;
    exports com.example.demo;
}
