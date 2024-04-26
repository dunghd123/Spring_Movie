package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Seat;
import ra.spring_movie.model.SeatCustom;

import java.util.List;

public interface SeatService {
     MessageResponse insertSeat(SeatCustom seat);
     MessageResponse updateSeat(int id,SeatCustom seat);
     MessageResponse deleteSeat(int seatid);
     List<Seat> viewSeat();

}
