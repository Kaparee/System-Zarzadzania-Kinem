package pl.cinema.Controller;

import pl.cinema.Service.ILoginService;
import pl.cinema.Service.IUsersService;

public abstract class BaseController {
    protected IUsersService usersService;
    protected ILoginService loginService;

    public void setUsersService(IUsersService usersService) {
        this.usersService = usersService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }
}
