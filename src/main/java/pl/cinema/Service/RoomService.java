package pl.cinema.Service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Util.HibernateUtil;

import java.lang.reflect.Array;
import java.util.List;

public class RoomService implements IRoomService{
    public List<String> getRows(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT DISTINCT(s.rowChar) FROM Seat s WHERE s.room.id = :id";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("id", id);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Long> getSeats(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT COUNT(s.number) FROM Seat s WHERE s.room.id = :id GROUP BY s.rowChar";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("id", id);
            return query.list();
        } catch (Exception e){
            e.printStackTrace();
            return List.of();
        }
    }
}
