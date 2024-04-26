package ra.spring_movie.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class changePassRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
