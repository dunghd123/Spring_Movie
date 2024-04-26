package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Cinema;

import java.util.List;

public interface CinemaService {
    public MessageResponse insertCinema(Cinema cinema);
    public MessageResponse updateCinema(Cinema cinema);

    public MessageResponse deleteCinema(int cinemaid);

    public List<Cinema> viewCinema();
}
