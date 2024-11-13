module org.example.javacwui {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javacwui to javafx.fxml;
    exports org.example.javacwui;
}