module com.example.projectiltanktest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectiltanktest to javafx.fxml;
    exports com.example.projectiltanktest;
}