package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;

import ra.spring_movie.repository.*;
import ra.spring_movie.services.RoomService;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private CinemaRepo cinemaRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    @Override
    public MessageResponse insertRoom(Room room) {
        Optional<Cinema> cinema= cinemaRepo.findById(room.getCinema().getId());
        if(cinema.isEmpty()){
            return MessageResponse.builder().message("Cinema does not exist!!!").build();
        }
        else {
            roomRepo.save(room);
            Optional<Room> roomCurrent= roomRepo.findByName(room.getName());
            if(roomCurrent.isPresent()){
                Room roomCur= roomCurrent.get();
                if(!roomCur.getSeats().isEmpty()){
                    for(Seat seat: room.getSeats()){
                        seat.setRoom(roomCur);
                        seatRepo.save(seat);
                    }
                }
            }
            return MessageResponse.builder().message("Insert room:"+room.getName()+" successfully!!!").build();
        }
    }
    @Override
    public MessageResponse updateRoom(Room room) {
        Optional<Room> roomCur= roomRepo.findById(room.getId());
        if(roomCur.isEmpty()){
            return MessageResponse.builder().message("room does not exist!!!").build();
        }else {
            roomRepo.save(room);
            return MessageResponse.builder().message("Update room:"+room.getName()+" successfully!!!").build();
        }
    }

    @Override
    public MessageResponse deleteRoom(int roomid) {
        Optional<Room> room= roomRepo.findById(roomid);
        if(room.isEmpty()) {
            return MessageResponse.builder().message("room does not exist!!!").build();
        }else {
            for(Seat seat: seatRepo.findAll()){
                //xoa cac seat trong room
                if(seat.getRoom().getId()==roomid){
                    //xoa ticket trong room
                    for(Ticket ticket: ticketRepo.findAll()){
                        if(ticket.getSeat().getId()==seat.getId()){
                            for(BillTicket billTicket: billTicketRepo.findAll()){
                                if(billTicket.getTicket().getId()==ticket.getId()){
                                    billTicketRepo.delete(billTicket);
                                }
                            }
                            ticketRepo.delete(ticket);
                        }
                    }
                    seatRepo.delete(seat);
                }
            }
            roomRepo.deleteById(roomid);
            return MessageResponse.builder().message("delete ("+roomid+" "+room.get().getName()+") successfully!!!").build();
        }
    }
    @Override
    public List<Room> viewRoom() {
        return roomRepo.findAll();
    }
}
