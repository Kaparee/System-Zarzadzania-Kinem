package pl.cinema.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.User;
import pl.cinema.Util.HibernateUtil;

public class LoginService implements ILoginService{
    public User verifyLogin(String login, String password){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT u FROM User u WHERE username = :login OR email = :login";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("login", login);

            User user = query.uniqueResult();
            if (user == null){
                return null;
            }

            String hashedPassword = user.getPasswordHash();
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

            if (result.verified){
                return user;
            }
            return null;
        } catch (HibernateException he) {
            he.printStackTrace();
            return null;
        }
    }
}
