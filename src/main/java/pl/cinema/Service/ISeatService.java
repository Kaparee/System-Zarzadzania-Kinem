package pl.cinema.Service;

import pl.cinema.Model.Seat;

import java.util.List;

public interface ISeatService {
    List<Boolean> isVip(Long id);
    Seat getSeatInfo(Long id, String rowChar, Integer number);
}
