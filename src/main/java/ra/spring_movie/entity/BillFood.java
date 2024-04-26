package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Table(name = "billfoods")
@Data
public class BillFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "quantity")
    @Min(value = 0,message = "so luong phai >=0")
    private int Quantity;

    //FK Bill
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billid",foreignKey = @ForeignKey(name = "FK_BillFood_Bill"))
    @JsonIgnoreProperties(value = "billFoods")
    private Bill bill;

    //FK Food
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foodid",foreignKey = @ForeignKey(name = "FK_BillFood_Food"))
    @JsonIgnoreProperties(value = "billFoods")
    private Food food;
}
