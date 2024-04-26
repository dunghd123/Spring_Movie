package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "foods")
@Data
public class Food {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "price")
    private double Price;
    @Column(name = "description")
    private String Description;
    @Column(name = "image")
    private String Image;
    @Column(name = "nameoffood")
    private String NameOfFood;
    @Column(name = "isactive")
    private boolean IsActive;

    @OneToMany(mappedBy = "food")
    @JsonIgnoreProperties(value = "food")
    Set<BillFood> billFoods;
}
