package ra.spring_movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InforCinema {
    private String nameOfCinema;
    private List<InforSchedule> scheduleList;

    public InforCinema(String nameOfCinema) {
        this.nameOfCinema = nameOfCinema;
    }
}

