package ra.spring_movie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InforSchedule {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endAt;
    private String roomName;

}
