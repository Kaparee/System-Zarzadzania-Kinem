package pl.cinema.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.Users;
import pl.cinema.Util.HibernateUtil;

public class LoginService implements ILoginService{
    public Users verifyLogin(String login, String password){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT u FROM Users u WHERE username = :login OR email = :login";
            Query<Users> query = session.createQuery(hql, Users.class);
            query.setParameter("login", login);

            Users users = query.uniqueResult();
            if (users == null){
                return null;
            }

            String hashedPassword = users.getPasswordHash();
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

            if (result.verified){
                return users;
            }
            return null;
        } catch (HibernateException he) {
            he.printStackTrace();
            return null;
        }
    }
}
