module org.openjx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjx to javafx.fxml;
    exports org.openjx;
}