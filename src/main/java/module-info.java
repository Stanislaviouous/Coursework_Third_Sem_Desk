module com.example.shopdelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.shopdelivery to javafx.fxml;
    exports com.example.shopdelivery;
    exports com.example.shopdelivery.controllers;
    opens com.example.shopdelivery.controllers to javafx.fxml;
    exports com.example.shopdelivery.entitys;
    opens com.example.shopdelivery.entitys to javafx.fxml;
}