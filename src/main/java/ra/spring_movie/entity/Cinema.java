package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "cinemas")
@Getter
@Setter
public class Cinema {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "address")
    private String Address;
    @Column(name = "description")
    private String Description;
    @Column(name = "code")
    private String Code;
    @Column(name = "nameofcinema")
    private String NameOfCinema;
    @Column(name = "isactive")
    private boolean IsActive;

    @OneToMany(mappedBy = "cinema")
    @JsonIgnoreProperties(value = "cinema")
    Set<Room> rooms;
}
