package pl.cinema.Service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.cinema.Model.Show;
import pl.cinema.Util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowService implements IShowService{
    public Map<String, List<Show>> getAllShowsGroupedByFilm(LocalDate startDate) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT s FROM Show s JOIN FETCH s.film f JOIN FETCH s.room r WHERE s.startTime >= :startDate AND s.startTime < :endDate ORDER BY f.title, r.name, s.startTime";
            Query<Show> query = session.createQuery(hql, Show.class);
            LocalDateTime startOfDay = startDate.atStartOfDay();
            LocalDateTime endOfDay = startDate.plusDays(1).atStartOfDay();
            query.setParameter("startDate", startOfDay);
            query.setParameter("endDate", endOfDay);
            List<Show> allShows = query.list();

            return allShows.stream()
                    .collect(Collectors.groupingBy(show -> show.getFilm().getTitle()));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
