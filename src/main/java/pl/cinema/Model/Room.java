package pl.cinema.Model;

import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {
    //CREATE TABLE room (
    //    id BIGSERIAL PRIMARY KEY,
    //    name VARCHAR(255) NOT NULL UNIQUE,
    //    capacity INT NOT NULL CHECK (capacity > 0),
    //    is3D BOOLEAN NOT NULL DEFAULT FALSE
    //);
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "is3D", nullable = false)
    private boolean is3D;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
    }

    public Room() {
    }
}
