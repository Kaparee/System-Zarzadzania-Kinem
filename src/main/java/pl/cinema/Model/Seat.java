package pl.cinema.Model;

import javax.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {
    //CREATE TABLE seat (
    //    id BIGSERIAL PRIMARY KEY,
    //    room_id BIGINT NOT NULL,
    //    row_char VARCHAR(1) NOT NULL,
    //    number INT NOT NULL,
    //    is_vip BOOLEAN NOT NULL DEFAULT FALSE,
    //    is_accessible BOOLEAN NOT NULL DEFAULT FALSE,
    //    CONSTRAINT uq_seat_in_room UNIQUE (room_id, row_char, number),
    //    CONSTRAINT fk_seat_room FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
    //);
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "row_char", nullable = false)
    private String rowChar;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "is_vip", nullable = false)
    private boolean isVip;

    @Column(name = "is_accessible", nullable = false)
    private boolean isAccessible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getRowChar() {
        return rowChar;
    }

    public void setRowChar(String rowChar) {
        this.rowChar = rowChar;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    public Seat() {
    }
}
