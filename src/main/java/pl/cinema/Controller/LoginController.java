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
        String login = usernameField.getText().toLowerCase();
        String password = passwordField.getText();

        int result = loginService.verifyLogin(login, password);
        switch (result){
            case 1 -> infoLabel.setText("Nazwa użytkownika: "+login +"\npassword: "+password+"\nUżytkownik");
            case 2 -> infoLabel.setText("Nazwa użytkownika: "+login +"\npassword: "+password+"\nAdmin");
            default -> infoLabel.setText("Błąd logowania"+result + " " +login + " "+password);
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
