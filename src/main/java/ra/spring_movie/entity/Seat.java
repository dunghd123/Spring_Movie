package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "seats")
@NoArgsConstructor
@Getter
@Setter
public class Seat {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "number")
    private int Number;
    @Column(name = "line")
    private String Line;
    @Column(name = "isactive")
    private boolean IsActive;

    //FK SeatStatus
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatstatusid",foreignKey = @ForeignKey(name = "FK_Seat_SeatStatus"))
    @JsonIgnoreProperties(value = "seats")
    private SeatStatus seatStatus;
    //FK SeatType
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seattypeid",foreignKey = @ForeignKey(name = "FK_Seat_SeatType"))
    @JsonIgnoreProperties(value = "seats")
    private SeatType seatType;
    //FK SeatType
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomid",foreignKey = @ForeignKey(name = "FK_Seat_Room"))
    @JsonIgnoreProperties(value = "seats")
    private Room room;

    @OneToMany(mappedBy = "seat")
    @JsonIgnoreProperties(value = "seat")
    Set<Ticket> tickets;

    public Seat(int number, String line, SeatStatus seatStatus, SeatType seatType, Room room) {
        Number = number;
        Line = line;
        this.seatStatus = seatStatus;
        this.seatType = seatType;
        this.room = room;
    }
}
