module tech.houssemnasri.gifx {
    requires javafx.controls;
    requires javafx.fxml;

    opens tech.houssemnasri.gifx to javafx.fxml;
    exports tech.houssemnasri.gifx;
    exports tech.houssemnasri.gifx.parser;
    opens tech.houssemnasri.gifx.parser to javafx.fxml;
    exports tech.houssemnasri.gifx.utils;
    opens tech.houssemnasri.gifx.utils to javafx.fxml;
    exports tech.houssemnasri.gifx.explorer;
    opens tech.houssemnasri.gifx.explorer to javafx.fxml;
}