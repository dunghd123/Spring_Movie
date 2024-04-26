package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.spring_movie.model.UserCustomDetail;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "userBuilder")
public class User extends UserCustomDetail {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "point")
    private int Point;
    @Column(name = "username",nullable = false)
    private String Username;
    @Column(name = "email",nullable = false)
    private String Email;
    @Column(name = "name")
    private String Name;
    @Column(name = "phonenumber",nullable = false)
    private String PhoneNumber;
    @Column(name = "password",nullable = false)
    private String Password;
    @Column(name = "isactive")
    private boolean IsActive;
    //FK UserStatus
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userstatusid",foreignKey = @ForeignKey(name = "FK_User_UserStatus"))
    @JsonIgnoreProperties(value = "users")
    private UserStatus userStatus;
    //FK RankCustomer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rankcustomerid",foreignKey = @ForeignKey(name = "FK_User_RankCustomer"))
    @JsonIgnoreProperties(value = "users")
    private RankCustomer rankCustomer;
    //FK Role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid",foreignKey = @ForeignKey(name = "FK_User_Role"))
    @JsonIgnoreProperties(value = "users")
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    Set<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    Set<ConfirmEmail> confirmEmails;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = "user")
    Set<Bill> bills;

}
