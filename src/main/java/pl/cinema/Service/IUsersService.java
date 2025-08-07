package pl.cinema.Service;

import pl.cinema.Model.Users;

import java.util.List;

public interface IUsersService {
    void createNewUser(String username, String password, String email, String phoneNumber, String firstName, String lastName);
}