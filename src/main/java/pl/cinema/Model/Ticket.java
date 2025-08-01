package pl.cinema.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {
    //CREATE TABLE ticket (
    //    id BIGSERIAL PRIMARY KEY,
    //    show_id BIGINT NOT NULL,
    //    user_id BIGINT,
    //    seat_id BIGINT,
    //    date_of_buy TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    //    final_price DECIMAL(10, 2) NOT NULL CHECK (final_price >= 0),
    //    status VARCHAR(50) NOT NULL,
    //    CONSTRAINT fk_ticket_show FOREIGN KEY (show_id) REFERENCES show(id) ON DELETE CASCADE,
    //    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    //    CONSTRAINT fk_ticket_seat FOREIGN KEY (seat_id) REFERENCES seat(id) ON DELETE SET NULL,
    //    CONSTRAINT uq_ticket_for_show_seat UNIQUE (show_id, seat_id)
    //);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name = "date_of_buy", nullable = false)
    private LocalDateTime dateOfBuy;

    @Column(name = "final_price", nullable = false)
    private BigDecimal final_price;

    @Column(name = "status", nullable = false)
    private String status;

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public LocalDateTime getDateOfBuy() {
        return dateOfBuy;
    }

    public void setDateOfBuy(LocalDateTime dateOfBuy) {
        this.dateOfBuy = dateOfBuy;
    }

    public BigDecimal getFinal_price() {
        return final_price;
    }

    public void setFinal_price(BigDecimal final_price) {
        this.final_price = final_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ticket() {
    }
}
