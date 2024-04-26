package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "seatstatus")
@Data
public class SeatStatus {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "code")
    private String Code;
    @Column(name = "namestatus")
    private String NameStatus;

    @OneToMany(mappedBy = "seatStatus")
    @JsonIgnoreProperties(value = "seatStatus")
    Set<Seat> seats;
}
