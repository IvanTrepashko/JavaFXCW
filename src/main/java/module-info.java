module com.course {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.course to javafx.fxml;
    opens com.course.client.controller to javafx.fxml;

    exports com.course;
    exports com.course.server.controller;
    exports com.course.client;
    exports com.course.client.controller;
}