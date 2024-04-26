package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.SeatStatus;

import java.util.List;

public interface SeatStatusService {
    MessageResponse insertSeatStatus(SeatStatus seatStatus);
    MessageResponse updateSeatStatus(int id,SeatStatus seatStatus);

    MessageResponse deleteSeatStatus(int id);

    List<SeatStatus> viewSeatStatus();
}
