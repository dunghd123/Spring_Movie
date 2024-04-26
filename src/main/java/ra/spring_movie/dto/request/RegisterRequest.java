package ra.spring_movie.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ra.spring_movie.entity.Role;
import ra.spring_movie.enums.RoleEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String name;
    private String password;
    private String email;
    private String phonenumber;
    private Role role;
}
