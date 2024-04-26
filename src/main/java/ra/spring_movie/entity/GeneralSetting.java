package ra.spring_movie.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "generalsettings")
@Data
public class GeneralSetting {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "breaktime")
    private Date BreakTime;
    @Column(name = "businesshours")
    private int BusinessHours;
    @Column(name = "closetime")
    private Date CloseTime;
    @Column(name = "fixedticketprice")
    private double FixedTicketPrice;
    @Column(name = "percentday")
    private int PercentDay;
    @Column(name = "percentweekend")
    private int PercentWeekend;
    @Column(name = "timebegintochange")
    private Date TimeBeginToChange;
}
