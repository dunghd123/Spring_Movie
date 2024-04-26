package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Table(name = "billtickets")
@Data
public class BillTicket {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "quantity")
    @Min(value = 0,message = "so luong phai >= 0")
    private int Quantity;
    //FK Bill
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billid",foreignKey = @ForeignKey(name = "FK_BillTicket_Bill"))
    @JsonIgnoreProperties(value = "billTickets")
    private Bill bill;
    //FK Ticket
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketid",foreignKey = @ForeignKey(name = "FK_BillTicket_Ticket"))
    @JsonIgnoreProperties(value = "billTickets")
    private Ticket ticket;

}
