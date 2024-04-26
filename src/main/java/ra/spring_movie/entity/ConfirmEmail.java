package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "confirmemails")
@Data
public class ConfirmEmail {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "expiredtime")
    private Date ExpiredTime;
    @Column(name = "confirmcode",updatable = false)
    private String ConfirmCode;
    @Column(name = "isconfirm")
    private boolean IsConfirm;
    //FK User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "FK_ConfirmEmail_User"))
    @JsonIgnoreProperties(value = "confirmEmails")
    private User user;
}
