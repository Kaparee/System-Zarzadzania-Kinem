package pl.cinema.Service;

import pl.cinema.Model.User;

public interface ILoginService {
    User verifyLogin(String login, String password);
}
