package ra.spring_movie.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "banners")
@Data
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "imageurl")
    private String ImageUrl;
    @Column(name = "title")
    private String Title;
}
