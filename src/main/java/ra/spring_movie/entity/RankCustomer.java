package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "rankcustomers")
@Data
public class RankCustomer {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "point")
    private int Point;
    @Column(name = "description")
    private String Description;
    @Column(name = "name")
    private String Name;
    @Column(name = "isactive")
    private boolean IsActive;

    @OneToMany(mappedBy = "rankCustomer")
    @JsonIgnoreProperties(value = "rankCustomer")
    Set<User> users;

    @OneToMany(mappedBy = "rankCustomer")
    @JsonIgnoreProperties(value = "rankCustomer")
    Set<Promotion> promotions;
}
