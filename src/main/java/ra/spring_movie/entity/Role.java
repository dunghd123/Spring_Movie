package ra.spring_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.spring_movie.enums.RoleEnums;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "code")
    private String Code;
    @Column(name = "rolename")
    @Enumerated(EnumType.STRING)
    private RoleEnums RoleName;

    @OneToMany(mappedBy = "role")
    @JsonIgnoreProperties(value = "role")
    Set<User> users;
}
