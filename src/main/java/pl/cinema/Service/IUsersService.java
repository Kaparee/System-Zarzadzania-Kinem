package pl.cinema.Service;

public interface IUsersService {
    void createNewUser(String username, String password, String email, String phoneNumber, String firstName, String lastName);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    boolean isPhoneTaken(String phone);
}