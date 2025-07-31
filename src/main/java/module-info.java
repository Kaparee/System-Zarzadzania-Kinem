module pl.cinema {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.cinema to javafx.fxml;
    exports pl.cinema;
}