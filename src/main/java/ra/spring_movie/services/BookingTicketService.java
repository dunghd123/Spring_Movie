package ra.spring_movie.services;

import ra.spring_movie.entity.Cinema;
import ra.spring_movie.model.InforCinema;
import ra.spring_movie.model.InforMovie;

import java.util.List;

public interface BookingTicketService {
    List<Integer> viewCinema(String moviename);
    InforMovie getData(String movieName);
}
