package ra.spring_movie.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;
import ra.spring_movie.model.ScheduleCustom;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.ScheduleService;


import java.util.*;

import static ra.spring_movie.helper.DateTimeHelper.subtractHoursFromDate;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private CinemaRepo cinemaRepo;

    private static String generateCode(Schedule schedule){
        return "S"+schedule.getId()+schedule.getMovie().getId()+schedule.getRoom().getId();
    }
    private static String generateName(Movie movie,Room room,Cinema cinema){

        return movie.getName()+"_"+room.getName()+"_"+cinema.getNameOfCinema();
    }
    private static Date countEndat(int movieDuration, Date startAt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startAt);
        // Thêm độ dài của phim vào thời gian bắt đầu
        calendar.add(Calendar.MINUTE, movieDuration);
        return calendar.getTime(); // Trả về thời gian kết thúc của phim
    }
    private static boolean checkSchedule(int roomid, Date startat,Date endat, List<Schedule> schedules){
        for(Schedule schedule: schedules){
            if(schedule.getRoom().getId()==roomid){
                // Kiểm tra xem thời gian bắt đầu và kết thúc của lịch chiếu hiện tại
                // có giao nhau với thời gian bắt đầu và kết thúc mới không
                if(!(endat.before(schedule.getStartAt())) || !(startat.after(schedule.getEndAt()))){
                    // Nếu có sự giao nhau, trả về true (có sự trùng lặp)
                    return true;
                }
            }
        }
        // Nếu không có sự trùng lặp, trả về false
        return false;
    }
    @Override
    public MessageResponse insertSchedule(ScheduleCustom scheduleCustom) {
        Optional<Movie> movie= movieRepo.findById(scheduleCustom.getMovieid());
        Optional<Room> room= roomRepo.findById(scheduleCustom.getRoomid());
        if(movie.isEmpty() || room.isEmpty()){
            return MessageResponse.builder().message("room or movie does not exist!!!").build();
        }else {
            Optional<Cinema> cinema= cinemaRepo.findById(room.get().getCinema().getId());
            Schedule schedule= new Schedule(scheduleCustom.getPrice(),subtractHoursFromDate(scheduleCustom.getStartat()),movie.get(), room.get());
            schedule.setIsActive(true);
            schedule.setName(generateName(movie.get(),room.get(), cinema.get()));
            schedule.setEndAt(countEndat(movie.get().getMovieDuration(),subtractHoursFromDate(scheduleCustom.getStartat())));
            scheduleRepo.save(schedule);
            //set code
            Schedule scheduleCur= scheduleRepo.findByName(schedule.getName()).orElseThrow();
            scheduleCur.setCode(generateCode(scheduleCur));
            scheduleRepo.save(scheduleCur);
            //kiem tra lich chieu
            List<Schedule> checkschedules = new ArrayList<>(scheduleRepo.findAll());
            checkschedules.removeIf(schedule1 -> schedule1.getId() == scheduleCur.getId());
            if(checkSchedule(scheduleCur.getRoom().getId(),
                    subtractHoursFromDate(scheduleCur.getStartAt()),
                    subtractHoursFromDate(scheduleCur.getEndAt()),
                    checkschedules)){
                scheduleRepo.delete(scheduleCur);
                return MessageResponse.builder().message("At this time, "+room.get().getName()+" is having another movie shown!!!").build();
            }else
                return MessageResponse.builder().message("Insert schedule successfully!!!").build();
        }
    }
    @Override
    public MessageResponse updateSchedule(int id, ScheduleCustom scheduleCustom) {
        Optional<Schedule> schedule= scheduleRepo.findById(id);
        Optional<Movie> movie = movieRepo.findById(scheduleCustom.getMovieid());
        Optional<Room> room= roomRepo.findById(scheduleCustom.getRoomid());
        //check valid
        if(schedule.isEmpty()){
            return MessageResponse.builder().message("Schedule does not exist!!!").build();
        }else {
            if(movie.isEmpty()|| room.isEmpty()){
                return MessageResponse.builder().message("room or movie does not exist!!!").build();
            }else {
                Schedule scheduleOld= schedule.get();
                Schedule scheduleCur= schedule.get();
                scheduleCur.setPrice(scheduleCustom.getPrice());
                scheduleCur.setStartAt(subtractHoursFromDate(scheduleCustom.getStartat()));
                scheduleCur.setMovie(movie.get());
                scheduleCur.setRoom(room.get());
                scheduleCur.setEndAt(countEndat(movie.get().getMovieDuration(),subtractHoursFromDate(scheduleCustom.getStartat())));
                scheduleRepo.save(scheduleCur);

                //kiem tra lich chieu
                List<Schedule> checkschedules = new ArrayList<>(scheduleRepo.findAll());
                checkschedules.removeIf(schedule1 -> schedule1.getId() == scheduleCur.getId());
                if(checkSchedule(scheduleCur.getRoom().getId(),
                        subtractHoursFromDate(scheduleCur.getStartAt()),
                        subtractHoursFromDate(scheduleCur.getEndAt()),
                        checkschedules)) {
                    scheduleRepo.delete(scheduleCur);
                    scheduleRepo.save(scheduleOld);
                    return MessageResponse.builder().message("At this time, " + room.get().getName() + " is having another movie shown!!!").build();
                }else {
                    return MessageResponse.builder().message("Update schedule successfully!!!").build();
                }
            }
        }
    }
    @Override
    public MessageResponse deleteSchedule(int scheduleid) {
        Optional<Schedule> schedule= scheduleRepo.findById(scheduleid);
        if(schedule.isEmpty()) {
            return MessageResponse.builder().message("Schedule does not exist!!!").build();
        }else {
            for(Ticket ticket: ticketRepo.findAll()){
                if(ticket.getSchedule().getId()==scheduleid){
                    for(BillTicket billTicket: billTicketRepo.findAll()){
                        if(billTicket.getTicket().getId()== ticket.getId()){
                            billTicketRepo.delete(billTicket);
                        }
                    }
                    ticket.setIsActive(false);
                    ticketRepo.save(ticket);
                }
            }
            schedule.get().setIsActive(false);
            scheduleRepo.save(schedule.get());
            return MessageResponse.builder().message("Delete schedule successfully!!!").build();
        }
    }
}
