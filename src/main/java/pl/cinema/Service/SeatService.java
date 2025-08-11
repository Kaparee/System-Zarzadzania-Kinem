package pl.cinema.Service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.Seat;
import pl.cinema.Util.HibernateUtil;

import java.util.List;

public class SeatService implements ISeatService {
    public List<Boolean> isVip(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT s.isVip FROM Seat s WHERE s.room.id = :id";
            Query<Boolean> query = session.createQuery(hql, Boolean.class);
            query.setParameter("id", id);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Seat getSeatInfo(Long id, String rowChar, Integer number){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT s FROM Seat s WHERE s.rowChar = :rowChar AND s.number = :number AND s.room.id = :id";
            Query<Seat> query = session.createQuery(hql, Seat.class);
            query.setParameter("rowChar", rowChar);
            query.setParameter("number", number);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
