package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "bills")
@Data
public class Bill {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "totalmoney")
    private double TotalMoney;
    @Column(name = "tradingcode")
    private String TradingCode;
    @Column(name = "createtime")
    private Date CreateTime;
    //CustomerId integer
    @Column(name = "name")
    private String Name;
    @Column(name = "updatetime")
    private Date UpdateTime;
    //PromotionId integer
    //BillStatusId integer
    @Column(name = "isactive")
    private boolean IsActive;

    //FK User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid",foreignKey = @ForeignKey(name = "FK_Bill_User"))
    @JsonIgnoreProperties(value = "bills")
    private User user;

    //FK Promotion
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotionid",foreignKey = @ForeignKey(name = "FK_Bill_Promotion"))
    @JsonIgnoreProperties(value = "bills")
    private Promotion promotion;

    //FK BillStatus
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billstatusid",foreignKey = @ForeignKey(name = "FK_Bill_BillStatus"))
    @JsonIgnoreProperties(value = "bills")
    private BillStatus billStatus;

    @OneToMany(mappedBy = "bill")
    @JsonIgnoreProperties(value = "bill")
    Set<BillFood> billFoods;

    @OneToMany(mappedBy = "bill")
    @JsonIgnoreProperties(value = "bill")
    Set<BillTicket> billTickets;

}
