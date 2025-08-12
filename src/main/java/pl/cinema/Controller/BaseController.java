package pl.cinema.Controller;

import pl.cinema.Service.*;

public abstract class BaseController {
    protected IUsersService usersService;
    protected ILoginService loginService;
    protected IRoomService roomService;
    protected ISeatService seatService;
    protected IShowService showService;

    public void setUsersService(IUsersService usersService) {
        this.usersService = usersService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public void setRoomService(IRoomService roomService) {
        this.roomService = roomService;
    }

    public void setSeatService(ISeatService seatService) {
        this.seatService = seatService;
    }

    public void setShowService(IShowService showService) {
        this.showService = showService;
    }
}
