package pl.cinema.Util;

import pl.cinema.Controller.BaseController;
import pl.cinema.Service.*;

public class ServiceInjector {
    public static void injectAllServices(BaseController controller){
        controller.setUsersService(new UsersService());
        controller.setLoginService(new LoginService());
        controller.setRoomService(new RoomService());
        controller.setSeatService(new SeatService());
        controller.setShowService(new ShowService());
    }
}
