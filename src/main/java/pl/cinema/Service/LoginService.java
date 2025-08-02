package pl.cinema.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.Users;
import pl.cinema.Util.HibernateUtil;

public class LoginService implements ILoginService{
    public int verifyLogin(String login, String password){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT u FROM Users u WHERE username = :login OR email = :login";
            Query<Users> query = session.createQuery(hql, Users.class);
            query.setParameter("login", login);

            Users users = query.uniqueResult();
            if (users == null){
                return 0;
            }

            String hashedPassword = users.getPasswordHash();
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

            if (result.verified){
                if (!users.isAdmin()){
                    return 1;
                } else if (users.isAdmin()) {
                    return 2;
                }
            }
            return 0;
        } catch (HibernateException he) {
            System.out.printf(String.valueOf(he));
            return 0;
        }
    }
}
