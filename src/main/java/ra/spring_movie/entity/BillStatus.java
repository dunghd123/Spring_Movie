package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "billstatus")
@Data
public class BillStatus {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "billStatus")
    @JsonIgnoreProperties(value = "billStatus")
    Set<Bill> bills;
}
