package ra.spring_movie.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;
import ra.spring_movie.model.SeatCustom;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.SeatService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    @Autowired
    private SeatTypeRepo seatTypeRepo;
    @Autowired
    private SeatStatusRepo seatStatusRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Override
    public MessageResponse insertSeat(SeatCustom seat) {
        Optional<SeatStatus> seatStatus= seatStatusRepo.findById(seat.getSeatStatusId());
        Optional<SeatType> seatType= seatTypeRepo.findById(seat.getSeatTypeId());
        Optional<Room> room= roomRepo.findById(seat.getRoomId());
        if(seatType.isEmpty() || seatStatus.isEmpty() || room.isEmpty()){
            return MessageResponse.builder().message("Check SeatType/SeatStatus/Room again!!!").build();
        }else {
            boolean check=true;
            for(Seat seatC : seatRepo.findAll()){
                if(Objects.equals(seatC.getLine(), seat.getLine())
                        && seatC.getNumber()==seat.getNumber()
                        && seatC.getRoom().getId()==seat.getRoomId()
                ){
                    check=false;
                    break;
                }
            }
            if(!check){
                return MessageResponse.builder().message("Seat has existed!!!").build();
            }else {
                Seat newseat = new Seat(seat.getNumber(),seat.getLine(),seatStatus.get(),seatType.get(),room.get());
                newseat.setIsActive(true);
                seatRepo.save(newseat);
                return MessageResponse.builder().message("insert seat successfully!!!").build();
            }
        }
    }

    @Override
    public MessageResponse updateSeat(int seatid,SeatCustom seat) {
        Optional<Seat> seatCurr= seatRepo.findById(seatid);
        Optional<SeatStatus> seatStatus= seatStatusRepo.findById(seat.getSeatStatusId());
        Optional<SeatType> seatType= seatTypeRepo.findById(seat.getSeatTypeId());
        Optional<Room> room= roomRepo.findById(seat.getRoomId());
        if(seatCurr.isEmpty()){
            return MessageResponse.builder().message("Seat does not exist!!!").build();
        }
        else {
            if(seatType.isEmpty() ||seatStatus.isEmpty()||room.isEmpty()){
                return MessageResponse.builder().message("Check SeatType/SeatStatus/Room again!!!").build();
            }
            else {
                Seat seatUpdate= seatCurr.get();
                seatUpdate.setNumber(seat.getNumber());
                seatUpdate.setSeatStatus(seatStatus.get());
                seatUpdate.setRoom(room.get());
                seatUpdate.setSeatType(seatType.get());
                seatUpdate.setLine(seat.getLine());
                seatUpdate.setIsActive(true);
                seatRepo.save(seatUpdate);
                return MessageResponse.builder().message("Update seat successfully!!!").build();
            }
        }
    }

    @Override
    public MessageResponse deleteSeat(int seatid) {
        Optional<Seat> seat= seatRepo.findById(seatid);
        if(seat.isEmpty()){
            return MessageResponse.builder().message("Seat does not exist!!!").build();
        }else {
            for(Ticket ticket: ticketRepo.findAll()){
                //xoa ticket cua seat
                if(ticket.getSeat().getId()==seat.get().getId() && ticket.isIsActive()){
                    for(BillTicket billTicket: billTicketRepo.findAll()){
                        //xoa billticket
                        if(billTicket.getTicket().getId()==ticket.getId()){
                            billTicketRepo.delete(billTicket);
                        }
                    }
                    ticket.setIsActive(false);
                    ticketRepo.save(ticket);
                }
            }
            seat.get().setIsActive(false);
            seatRepo.save(seat.get());
            return MessageResponse.builder().message("Delete seat successfully!!!").build();
        }
    }
    @Override
    public List<Seat> viewSeat() {
        return seatRepo.findAll();
    }
}
