module com.elfwegos.dropezone {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.fxmlkit;
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.javafx;
    requires com.google.gson;

    opens com.elfwegos.dropezone to javafx.fxml, com.google.gson;
    exports com.elfwegos.dropezone;
    exports com.elfwegos.dropezone.controllers;
    opens com.elfwegos.dropezone.controllers to javafx.fxml;
    exports com.elfwegos.dropezone.services;
    opens com.elfwegos.dropezone.services to javafx.fxml;
    opens com.elfwegos.dropezone.models to com.google.gson;
    opens com.elfwegos.dropezone.utils to com.google.gson;

}
