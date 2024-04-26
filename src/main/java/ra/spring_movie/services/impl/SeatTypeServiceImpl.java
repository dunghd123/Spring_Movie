package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;

import ra.spring_movie.entity.BillTicket;
import ra.spring_movie.entity.Seat;
import ra.spring_movie.entity.SeatType;
import ra.spring_movie.entity.Ticket;
import ra.spring_movie.repository.BillTicketRepo;
import ra.spring_movie.repository.SeatRepo;
import ra.spring_movie.repository.SeatTypeRepo;
import ra.spring_movie.repository.TicketRepo;
import ra.spring_movie.services.SeatTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class SeatTypeServiceImpl implements SeatTypeService {
    @Autowired
    private SeatTypeRepo seatTypeRepo;
    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    @Override
    public MessageResponse insertSeatType(SeatType seatType) {
        seatTypeRepo.save(seatType);
        return MessageResponse.builder().message("insert successfully!!!").build();
    }

    @Override
    public MessageResponse updateSeatType(int id, SeatType seatType) {
        Optional<SeatType> seatTypeCur= seatTypeRepo.findById(id);
        if(seatTypeCur.isEmpty()){
            return MessageResponse.builder().message("seatType does not exist!!!").build();
        }else {
            seatTypeCur.get().setNameType(seatType.getNameType());
            seatTypeRepo.save(seatTypeCur.get());
            return MessageResponse.builder().message("update seatType successfully!!!").build();
        }
    }

    @Override
    public MessageResponse deleteSeatType(String nameType) {
        Optional<SeatType> seatTypeCur= seatTypeRepo.findByNameType(nameType);
        if(seatTypeCur.isEmpty()){
            return MessageResponse.builder().message("seatType does not exist!!!").build();
        }else {
            for(Seat seat: seatRepo.findAll()){
                if(seat.getSeatType().getId()==seatTypeCur.get().getId()){
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
            seatTypeRepo.delete(seatTypeCur.get());
            return MessageResponse.builder().message("Delete seatType successfully!!!").build();
        }
    }
    @Override
    public List<SeatType> viewSeatType() {
        return seatTypeRepo.findAll();
    }
}
