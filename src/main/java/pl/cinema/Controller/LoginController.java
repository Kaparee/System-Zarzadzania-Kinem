package pl.cinema.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.cinema.Model.Users;
import pl.cinema.Util.ServiceInjector;

public class LoginController extends BaseController{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label infoLabel;
    @FXML
    private Button loginButton, registerButton;

    @FXML
    private void initialize() {
        ServiceInjector.injectAllServices(this);
    }

    @FXML
    public void onLoginButtonClicked(){
        String login = usernameField.getText();
        String password = passwordField.getText();

        Users user = loginService.verifyLogin(login, password);
        String role;
        if (user != null){
            if (user.isAdmin()){
                role = "admin";
            } else {
                role = "user";
            }
            infoLabel.setText("Welcome " + user.getUsername()+"!\nYour email: " + user.getEmail()+"\nYour number: " + user.getPhoneNumber()+"\nYour name and surname: "+user.getFirstName() + " " + user.getLastName()+"\nYour role: "+role);
        } else {
            infoLabel.setText("Wrong login or password");
        }

    }

    @FXML
    public void onRegisterButtonClicked(javafx.event.ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/cinema/register-view.fxml"));
        try {
            Parent root = fxmlLoader.load();
            RegisterController registerController = fxmlLoader.getController();
            ServiceInjector.injectAllServices(registerController);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
