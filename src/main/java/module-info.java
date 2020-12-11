module com.course {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens com.course to javafx.fxml;
    opens com.course.client.controller to javafx.fxml;
    opens com.course.entity to javafx.base;
    opens com.course.client.viewmodel to javafx.base;

    exports com.course;
    exports com.course.server.controller;
    exports com.course.client;
    exports com.course.client.controller;
}