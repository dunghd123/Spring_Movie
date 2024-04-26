package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "seattypes")
@Data
public class SeatType {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "nametype")
    private String NameType;

    @OneToMany(mappedBy = "seatType")
    @JsonIgnoreProperties(value = "seatType")
    Set<Seat> seats;
}
