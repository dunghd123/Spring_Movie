package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "price")
    private double Price;
    @Column(name = "startat")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date StartAt;
    @Column(name = "endat")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date EndAt;
    @Column(name = "code")
    private String Code;
    @Column(name = "name")
    private String Name;
    @Column(name = "isactive")
    private boolean IsActive;
    //FK Movie
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movieid",foreignKey = @ForeignKey(name = "FK_Schedule_Movie"))
    @JsonIgnoreProperties(value = "schedules")
    private Movie movie;
    //FK Room
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomid",foreignKey = @ForeignKey(name = "FK_Schedule_Room"))
    @JsonIgnoreProperties(value = "schedules")
    private Room room;

    @OneToMany(mappedBy = "schedule")
    @JsonIgnoreProperties(value = "schedule")
    Set<Ticket> tickets;

    public Schedule(double price, Date startAt, Movie movie, Room room) {
        Price = price;
        StartAt = startAt;
        this.movie = movie;
        this.room = room;
    }
}
