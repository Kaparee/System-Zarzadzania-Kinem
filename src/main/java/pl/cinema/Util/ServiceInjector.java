package pl.cinema.Util;

import pl.cinema.Controller.BaseController;
import pl.cinema.Service.LoginService;
import pl.cinema.Service.UsersService;

public class ServiceInjector {
    public static void injectAllServices(BaseController controller){
        controller.setUsersService(new UsersService());
        controller.setLoginService(new LoginService());
    }
}
