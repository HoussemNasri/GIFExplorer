module tech.houssemnasri.gifx {
    requires javafx.controls;
    requires javafx.fxml;

    opens tech.houssemnasri.gifx to javafx.fxml;
    exports tech.houssemnasri.gifx;
}