package pl.cinema.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.cinema.Util.ServiceInjector;

public class RegisterController extends BaseController{
    @FXML
    private TextField usernameField, emailField, phoneField, nameField, surnameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerButton, clearButton, returnButton;

    @FXML
    private void initialize() {
        ServiceInjector.injectAllServices(this);
    }

    @FXML
    public void onRegisterButtonClicked(){
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();

        if (username.isBlank() || password.isBlank() || email.isBlank() || phone.isBlank() || name.isBlank() || surname.isBlank()){
            System.out.println("Write correct information");
            return;
        }

        if (validateAll()){
            try {
                usersService.createNewUser(username, password, email, phone, name, surname);
                onClearButtonClicked();
                onReturnButtonClicked();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onClearButtonClicked(){
        usernameField.setText(null);
        passwordField.setText(null);
        emailField.setText(null);
        phoneField.setText(null);
        nameField.setText(null);
        surnameField.setText(null);
    }

    public boolean validateAll() {
        boolean isValid = true;
        resetStyles();
        String username = usernameField.getText().trim();
        if (!isValidUsername(username) || usersService.isUsernameTaken(username)) {
            usernameField.setStyle("-fx-background-color: red");
            isValid = false;
        }
        String password = passwordField.getText().trim();
        if (!isValidPassword(password)) {
            passwordField.setStyle("-fx-background-color: red");
            isValid = false;
        }
        String email = emailField.getText().trim();
        if (!isValidEmail(email) || usersService.isEmailTaken(email)) {
            emailField.setStyle("-fx-background-color: red");
            isValid = false;
        }
        String phone = phoneField.getText().trim();
        if (!isValidPhone(phone) || usersService.isPhoneTaken(phone)) {
            phoneField.setStyle("-fx-background-color: red");
            isValid = false;
        }
        return isValid;
    }

    private void resetStyles() {
        usernameField.setStyle("");
        passwordField.setStyle("");
        emailField.setStyle("");
        phoneField.setStyle("");
    }

    public boolean isValidUsername(String username){
        return !username.contains("@") && username.length() >= 3;
    }

    public boolean isValidPassword(String password){
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$";
        return password.matches(regex);
    }

    public boolean isValidEmail(String email){
        String regex = "^[\\p{L}0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
    }

    public boolean isValidPhone(String phone){
        String regex = "^(\\d{9}|\\d{3}[- ]\\d{3}[- ]\\d{3})$";
        return phone.matches(regex);
    }

    public void onReturnButtonClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/cinema/fxml/login-view.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
