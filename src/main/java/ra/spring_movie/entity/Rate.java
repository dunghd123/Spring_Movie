package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "rates")
@Getter
@Setter
public class Rate {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "description")
    private String Description;
    @Column(name = "code")
    private String Code;

    @OneToMany(mappedBy = "rate")
    @JsonIgnoreProperties(value = "rate")
    Set<Movie> movies;
}
