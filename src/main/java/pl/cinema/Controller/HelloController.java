package pl.cinema.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.cinema.Model.Users;
import pl.cinema.Service.UsersInterface;
import pl.cinema.Service.UsersService;

import java.util.List;

public class HelloController {
    private UsersInterface usersInterface = new UsersService();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        List<Users> allUsers = usersInterface.getAllUsers();

        for (Users user : allUsers) {
            System.out.println("ID: " + user.getId() + ", Nazwa: " + user.getUsername() + ", Email: " + user.getEmail());
        }
    }
}