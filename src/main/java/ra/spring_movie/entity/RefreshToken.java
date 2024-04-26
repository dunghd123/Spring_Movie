package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "refreshtokens")
@Data
public class RefreshToken {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "token")
    private String Token;
    @Column(name = "expiredtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ExpiredTime;
    //FK User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "FK_RefreshToken_User"))
    @JsonIgnoreProperties(value = "refreshTokens")
    private User user;
}
