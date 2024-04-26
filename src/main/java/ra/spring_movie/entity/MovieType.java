package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "movietypes")
@Getter
@Setter
public class MovieType {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "movietypename")
    private String MovieTypeName;
    @Column(name = "isactive")
    private boolean IsActive;

    @OneToMany(mappedBy = "movieType")
    @JsonIgnoreProperties(value = "movieType")
    Set<Movie> movies;
}
