package pl.cinema.Service;

import java.util.List;

public interface IRoomService {
    List<String> getRows(Long id);
    List<Long> getSeats(Long id);
}
