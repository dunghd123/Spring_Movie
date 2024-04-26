package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "tickets")
@Data
public class Ticket {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "code")
    private String Code;
    @Column(name = "priceticket")
    private double PriceTicket;
    @Column(name = "isactive")
    private boolean IsActive;
    //FK Schedule
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleid",foreignKey = @ForeignKey(name = "FK_Ticket_Schedule"))
    @JsonIgnoreProperties(value = "tickets")
    private Schedule schedule;
    //FK Seat
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatid",foreignKey = @ForeignKey(name = "FK_Ticket_Seat"))
    @JsonIgnoreProperties(value = "tickets")
    private Seat seat;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnoreProperties(value = "ticket")
    Set<BillTicket> billTickets;
}
