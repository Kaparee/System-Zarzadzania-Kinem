package pl.cinema.Service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.Users;
import pl.cinema.Util.HibernateUtil;

import java.util.List;

public class UsersService implements UsersInterface {

    public List<Users> getAllUsers(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT u FROM Users u";
            Query<Users> query = session.createQuery(hql, Users.class);
            return query.list();
        } catch (HibernateException he){
            return null;
        }
    }
}
