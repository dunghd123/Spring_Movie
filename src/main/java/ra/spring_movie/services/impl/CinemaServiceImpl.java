package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.CinemaService;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaRepo cinemaRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    @Override
    public MessageResponse insertCinema(Cinema cinema) {
        cinema.setIsActive(false);
        cinemaRepo.save(cinema);
        Optional<Cinema> cinemaCurrent= cinemaRepo.findByNameOfCinema(cinema.getNameOfCinema());
        if(cinemaCurrent.isPresent()){
            Cinema cinemaCur= cinemaCurrent.get();
            if(!cinema.getRooms().isEmpty()){
                for(Room room: cinema.getRooms()){
                    room.setCinema(cinemaCur);
                    roomRepo.save(room);
                }
            }
        }
        return MessageResponse.builder().message("Insert cinema:"+cinema.getNameOfCinema()+" successfully!!!").build();
    }

    @Override
    public MessageResponse updateCinema(Cinema cinema) {
        Optional<Cinema> cinemaCurrent= cinemaRepo.findById(cinema.getId());
        if(cinemaCurrent.isEmpty()){
            return MessageResponse.builder().message("cinema:"+cinema.getNameOfCinema()+" does not exist!!!").build();
        }else {
            Cinema cinemaCur= cinemaCurrent.get();
            cinemaCur.setNameOfCinema(cinema.getNameOfCinema());
            cinemaCur.setDescription(cinema.getDescription());
            cinemaCur.setCode(cinema.getCode());
            cinemaCur.setAddress(cinema.getAddress());
            cinemaCur.setIsActive(cinema.isIsActive());
            cinemaRepo.save(cinemaCur);
            return MessageResponse.builder().message("Update information of cinema:"+cinema.getNameOfCinema()+" successfully!!!").build();
        }
    }
    @Override
    public MessageResponse deleteCinema(int cinemaid) {
        Optional<Cinema> cinema= cinemaRepo.findById(cinemaid);
        if(cinema.isEmpty()){
            return MessageResponse.builder().message("cinema does not exist!!!").build();
        }else {
            //xoa cac room cua rap
            for(Room room: roomRepo.findAll()){
                if(room.getCinema().getId()==cinemaid){
                    //xoa cac schedule cua room
                    for(Schedule schedule: scheduleRepo.findAll()){
                        if(schedule.getRoom().getId()== room.getId()){
                            //xoa cac ticket cua schedule
                            for(Ticket ticket: ticketRepo.findAll()){
                                if(ticket.getSchedule().getId()==schedule.getId()){
                                    //xoa cac billticket cua ticket
                                    for(BillTicket btk: billTicketRepo.findAll()){
                                        if(btk.getTicket().getId()==ticket.getId()){
                                            billTicketRepo.delete(btk);
                                        }
                                    }
                                    ticketRepo.delete(ticket);
                                }
                            }
                            scheduleRepo.delete(schedule);
                        }
                    }
                    //xoa cac seat trong room
                    for(Seat seat: seatRepo.findAll()){
                        if(seat.getRoom().getId()== room.getId()){
                            //xoa ticket cua seat
                            for(Ticket ticket: ticketRepo.findAll()){
                                if(ticket.getSeat().getId()==seat.getId()){
                                    //xoa billticket cua ticket
                                    for(BillTicket btk: billTicketRepo.findAll()){
                                        if(btk.getTicket().getId()==ticket.getId()){
                                            billTicketRepo.delete(btk);
                                        }
                                    }
                                    ticketRepo.delete(ticket);
                                }
                            }
                            seatRepo.delete(seat);
                        }
                    }
                    roomRepo.delete(room);
                }
            }
            cinemaRepo.deleteById(cinemaid);
            return MessageResponse.builder().message(
                    "Delete cinema ("+cinemaid+": "+cinema.get().getNameOfCinema()+") successfully!!!").build();
        }
    }
    @Override
    public List<Cinema> viewCinema() {
        return cinemaRepo.findAll();
    }
}
