package pl.cinema.Model;

import javax.persistence.*;

@Entity
@Table(name = "show_seat")
public class ShowSeat {
    //CREATE TABLE show_seat (
    //    id BIGSERIAL PRIMARY KEY,
    //    show_id BIGINT NOT NULL,
    //    seat_id BIGINT NOT NULL,
    //    is_occupied BOOLEAN NOT NULL DEFAULT FALSE,
    //    ticket_id BIGINT UNIQUE,
    //    CONSTRAINT uq_show_seat_pair UNIQUE (show_id, seat_id),
    //    CONSTRAINT fk_show_seat_show FOREIGN KEY (show_id) REFERENCES show(id) ON DELETE CASCADE,
    //    CONSTRAINT fk_show_seat_seat FOREIGN KEY (seat_id) REFERENCES seat(id) ON DELETE CASCADE,
    //    CONSTRAINT fk_show_seat_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE SET NULL,
    //    CONSTRAINT chk_occupied_with_ticket CHECK (
    //        (is_occupied = TRUE AND ticket_id IS NOT NULL) OR
    //        (is_occupied = FALSE AND ticket_id IS NULL)
    //    )
    //);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @ManyToOne
    @JoinColumn(name = "ticket_id", unique = true)
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public ShowSeat() {
    }
}