package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.*;


import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "movieduration")
    private int MovieDuration;
    @Column(name = "endtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date EndTime;
    @Column(name = "premieredate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date PremiereDate;
    @Column(name = "description")
    private String Description;
    @Column(name = "director")
    private String Director;
    @Column(name = "image")
    private String Image;
    @Column(name = "heroimage")
    private String HeroImage;
    @Column(name = "language")
    private String Language;
    @Column(name = "name")
    private String Name;
    @Column(name = "trailer")
    private String Trailer;
    @Column(name = "isactive")
    private boolean IsActive;
    //FK MovieType
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movietypeid",foreignKey = @ForeignKey(name = "FK_Movie_MovieType"))
    @JsonIgnoreProperties(value = "movies")
    private MovieType movieType;
    //FK MovieType
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rateid",foreignKey = @ForeignKey(name = "FK_Movie_Rate"))
    @JsonIgnoreProperties(value = "movies")
    private Rate rate;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties(value = "movie")
    Set<Schedule> schedules;

    public Movie(int movieDuration, Date endTime, Date premiereDate, String description,
                 String director, String image, String heroImage, String language, String name,
                 String trailer, MovieType movieType, Rate rate) {
        MovieDuration = movieDuration;
        EndTime = endTime;
        PremiereDate = premiereDate;
        Description = description;
        Director = director;
        Image = image;
        HeroImage = heroImage;
        Language = language;
        Name = name;
        Trailer = trailer;
        this.movieType = movieType;
        this.rate = rate;
    }
}
