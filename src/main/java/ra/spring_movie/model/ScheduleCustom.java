package ra.spring_movie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCustom {
    private double price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startat;
    private int movieid;
    private int roomid;
}
