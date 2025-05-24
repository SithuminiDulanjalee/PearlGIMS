module lk.ijse.pearlgims {
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.jfoenix;
    requires javafx.controls;


    opens lk.ijse.pearlgims to javafx.fxml;
    opens lk.ijse.pearlgims.controller to javafx.fxml;
    opens lk.ijse.pearlgims.dto.tm to javafx.base;

    exports lk.ijse.pearlgims;
}