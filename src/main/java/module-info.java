module com.course {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.course to javafx.fxml;
    exports com.course;
}