package pl.cinema.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "show")
public class Show {
//CREATE TABLE show (
//    id BIGSERIAL PRIMARY KEY,
//    film_id BIGINT NOT NULL,
//    room_id BIGINT NOT NULL,
//    start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
//    price_base DECIMAL(10, 2) NOT NULL CHECK (price_base >= 0),
//    CONSTRAINT fk_show_film FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE,
//    CONSTRAINT fk_show_room FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
//);
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "price_base", nullable = false)
    private BigDecimal priceBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getPriceBase() {
        return priceBase;
    }

    public void setPriceBase(BigDecimal priceBase) {
        this.priceBase = priceBase;
    }

    public Show() {
    }
}
