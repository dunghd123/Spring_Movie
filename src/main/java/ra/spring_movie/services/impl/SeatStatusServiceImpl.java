package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.BillTicket;
import ra.spring_movie.entity.Seat;
import ra.spring_movie.entity.SeatStatus;
import ra.spring_movie.entity.Ticket;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.SeatStatusService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SeatStatusServiceImpl implements SeatStatusService {
    @Autowired
    private SeatStatusRepo seatStatusRepo;
    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    private static String generateCode(int id) {
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        String code = String.format("%d_%02d", id, randomNumber);
        return code;
    }
    @Override
    public MessageResponse insertSeatStatus(SeatStatus seatStatus) {
        seatStatusRepo.save(seatStatus);
        Optional<SeatStatus> seatStatusCur= seatStatusRepo.findByNameStatus(seatStatus.getNameStatus());
        if(seatStatusCur.isPresent()){
            seatStatusCur.get().setCode(generateCode(seatStatusCur.get().getId()));
            seatStatusRepo.save(seatStatusCur.get());
            return MessageResponse.builder().message("Insert successfully!!!").build();
        }else {
            return MessageResponse.builder().message("Insert failed!!!").build();
        }
    }

    @Override
    public MessageResponse updateSeatStatus(int id, SeatStatus seatStatus) {
        Optional<SeatStatus> status= seatStatusRepo.findById(id);
        if(status.isEmpty()){
            return MessageResponse.builder().message("Seat Status does not exist!!!").build();
        }else {
            status.get().setNameStatus(seatStatus.getNameStatus());
            seatStatusRepo.save(status.get());
            return MessageResponse.builder().message("Update successfully!!!").build();
        }
    }

    @Override
    public MessageResponse deleteSeatStatus(int id) {
        Optional<SeatStatus> status= seatStatusRepo.findById(id);
        if(status.isEmpty()){
            return MessageResponse.builder().message("Seat Status does not exist!!!").build();
        }else {
            for(Seat seat: seatRepo.findAll()){
                if(seat.getSeatType().getId()==id){
                    for(Ticket ticket: ticketRepo.findAll()){
                        if(ticket.getSeat().getId()== seat.getId()){
                            for(BillTicket billTicket: billTicketRepo.findAll()){
                                if(billTicket.getTicket().getId()== ticket.getId()){
                                    billTicketRepo.delete(billTicket);
                                }
                            }
                            ticket.setIsActive(false);
                            ticketRepo.save(ticket);
                        }
                    }
                    seat.setIsActive(false);
                    seatRepo.save(seat);
                }
            }
            seatStatusRepo.delete(status.get());
            return MessageResponse.builder().message("Delete seat status successfully!!!").build();
        }
    }
    @Override
    public List<SeatStatus> viewSeatStatus() {
        return seatStatusRepo.findAll();
    }
}
