package pl.cinema.Util;


import pl.cinema.Model.User;

public final class SessionManager {

    private static SessionManager instance;
    private User currentUser;

    // Prywatny konstruktor zapobiega tworzeniu nowych instancji
    private SessionManager() { }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}
