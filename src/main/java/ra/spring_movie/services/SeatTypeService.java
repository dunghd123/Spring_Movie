package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.SeatType;

import java.util.List;

public interface SeatTypeService {
    MessageResponse insertSeatType(SeatType seatType);
    MessageResponse updateSeatType(int id,SeatType seatType);
    MessageResponse deleteSeatType(String nameType);
    List<SeatType> viewSeatType();
}
