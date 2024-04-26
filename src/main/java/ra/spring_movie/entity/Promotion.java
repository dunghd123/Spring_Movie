package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "promotions")
@Data
public class Promotion {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "percent")
    private int Percent;
    @Column(name = "quantity")
    private int Quantity;
    @Column(name = "type")
    private String Type;
    @Column(name = "starttime")
    private Date StartTime;
    @Column(name = "endtime")
    private Date EndTime;
    @Column(name = "description")
    private String Description;
    @Column(name = "name")
    private String Name;
    @Column(name = "isactive")
    private boolean IsActive;

    //FK RankCustomer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rankcustomerid",foreignKey = @ForeignKey(name = "FK_Promotion_RankCustomer"))
    @JsonIgnoreProperties(value = "promotions")
    private RankCustomer rankCustomer;

    @OneToMany(mappedBy = "promotion")
    @JsonIgnoreProperties(value = "promotion")
    Set<Bill> bills;
}
