package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "userstatus")
@Data
public class UserStatus {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "code")
    private String Code;
    @Column(name = "name")
    private String Name;
    @OneToMany(mappedBy = "userStatus")
    @JsonIgnoreProperties(value = "userStatus")
    Set<User> users;
}
