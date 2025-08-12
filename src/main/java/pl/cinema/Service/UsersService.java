package pl.cinema.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.User;
import pl.cinema.Util.HibernateUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UsersService implements IUsersService {

    public void createNewUser(String username, String password, String email, String phoneNumber, String firstName, String lastName){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAdmin(false);
            user.setRegistrationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Wystąpił błąd podczas tworzenia konta");
        }
    }

    public boolean isUsernameTaken(String username){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
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
            String hql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
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
            String hql = "SELECT COUNT(u) FROM User u WHERE u.phoneNumber = :phone";
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
