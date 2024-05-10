module com.usta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.usta to javafx.fxml;
    opens com.usta.controller to javafx.fxml;
    opens com.usta.model to javafx.fxml;


    exports com.usta;
    exports com.usta.controller;
    exports com.usta.model;


}
