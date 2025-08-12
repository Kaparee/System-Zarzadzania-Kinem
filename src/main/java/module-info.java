module pl.cinema {
        requires javafx.controls;
        requires javafx.fxml;
        requires org.hibernate.orm.core;
        requires java.naming;
        requires java.sql;
        requires bcrypt;
        requires java.persistence;
    requires net.bytebuddy;

    opens pl.cinema to javafx.fxml, org.hibernate.orm.core;
        exports pl.cinema;
        exports pl.cinema.Model;
        opens pl.cinema.Model to javafx.fxml, org.hibernate.orm.core;
        exports pl.cinema.Controller;
        opens pl.cinema.Controller to javafx.fxml, org.hibernate.orm.core;
        exports pl.cinema.Service;
        opens pl.cinema.Service to javafx.fxml, org.hibernate.orm.core;
        exports pl.cinema.Util;
        opens pl.cinema.Util to javafx.fxml, org.hibernate.orm.core;
        }