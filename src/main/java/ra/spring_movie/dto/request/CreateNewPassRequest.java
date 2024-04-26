package ra.spring_movie.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateNewPassRequest {
    private String username;
    private String newpass;
}
