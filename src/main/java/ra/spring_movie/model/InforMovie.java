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
public class InforMovie {
    private String name;
    private String movieType;
    private int duration;
    private List<InforCinema> cinemaList;
}
