package ra.spring_movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCustom {
    private int capacity;
    private String description;
    private String name;
    private int type;
    private int cinemaid;
}
