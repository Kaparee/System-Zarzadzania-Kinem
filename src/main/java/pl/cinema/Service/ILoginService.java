package pl.cinema.Service;

import pl.cinema.Model.Users;

public interface ILoginService {
    Users verifyLogin(String login, String password);
}
