package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "capacity")
    private int Capacity;
    @Column(name = "type")
    private int Type;
    @Column(name = "description")
    private String Description;
    @Column(name = "code")
    private String Code;
    @Column(name = "name")
    private String Name;
    @Column(name = "isactive")
    private boolean IsActive;
    //FK Cinema
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinemaid",foreignKey = @ForeignKey(name = "FK_Room_Cinema"))
    @JsonIgnoreProperties(value = "rooms")
    private Cinema cinema;

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = "room")
    Set<Seat> seats;

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = "room")
    Set<Schedule> schedules;
}
