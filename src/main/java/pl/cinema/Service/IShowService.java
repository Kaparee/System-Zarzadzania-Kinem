package pl.cinema.Service;

import pl.cinema.Model.Show;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IShowService {
    Map<String, List<Show>> getAllShowsGroupedByFilm(LocalDate startDate);
}
