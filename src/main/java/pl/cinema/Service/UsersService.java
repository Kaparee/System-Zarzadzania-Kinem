package pl.cinema.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.Users;
import pl.cinema.Util.HibernateUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class UsersService implements IUsersService {

    public void createNewUser(String username, String password, String email, String phoneNumber, String firstName, String lastName){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Users users = new Users();
            users.setUsername(username);
            users.setPasswordHash(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
            users.setEmail(email);
            users.setPhoneNumber(phoneNumber);
            users.setFirstName(firstName);
            users.setLastName(lastName);
            users.setAdmin(false);
            users.setRegistrationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            session.save(users);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Wystąpił błąd podczas tworzenia konta");
        }
    }

    public boolean isUsernameTaken(String username){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT COUNT(u) FROM Users u WHERE u.username = :username";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            Long result = query.uniqueResult();
            return result > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailTaken(String email){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT COUNT(u) FROM Users u WHERE u.email = :email";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            Long result = query.uniqueResult();
            return result > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPhoneTaken(String phone){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT COUNT(u) FROM Users u WHERE u.phoneNumber = :phone";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("phone", phone);
            Long result = query.uniqueResult();
            return result > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
