package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.entity.*;
import ra.spring_movie.model.InforCinema;
import ra.spring_movie.model.InforMovie;
import ra.spring_movie.model.InforSchedule;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.BookingTicketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingTicketServiceImpl implements BookingTicketService {
    @Autowired
    private CinemaRepo cinemaRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private MovieTypeRepo movieTypeRepo;
    @Override
    public List<Integer> viewCinema(String moviename){
        return cinemaRepo.findCinema(moviename);
    }
    @Override
    public InforMovie getData(String movieName) {
        List<Integer> listIdCinema= cinemaRepo.findCinema(movieName);
        List<InforCinema> inforCinemas= new ArrayList<>();
        //lay ra danh sach ten cac rap chieu phim do
        for(Cinema cinema: cinemaRepo.findAll()){
            for(int i: listIdCinema){
                if(cinema.getId()==i){
                    inforCinemas.add(new InforCinema(cinema.getNameOfCinema()));
                }
            }
        }
        Optional<Movie> movie= movieRepo.findByName(movieName);
        if(movie.isPresent()){
            //tim tat ca lich chieu phim do cua tung rap
            for(InforCinema inforCinema: inforCinemas){
                List<InforSchedule> infoSchedules= new ArrayList<>();
                Optional<Cinema> cinema= cinemaRepo.findByNameOfCinema(inforCinema.getNameOfCinema());
                for(Room room: roomRepo.findAll()){
                    if(room.getCinema().getId()==cinema.get().getId()){
                        for(Schedule schedule: scheduleRepo.findAll()){
                            if(schedule.getRoom().getId()== room.getId()
                                    && schedule.getMovie().getName().equals(movieName)
                                    && schedule.isIsActive()){
                                infoSchedules.add(new InforSchedule(schedule.getStartAt(),schedule.getEndAt(),room.getName()));
                            }
                        }
                    }
                }
                inforCinema.setScheduleList(infoSchedules);
            }
        }
        //lay ra loai phim
        Optional<MovieType> movieType= movieTypeRepo.findById(movie.get().getMovieType().getId());
        return new InforMovie(
                movie.get().getName(),movieType.get().getMovieTypeName(),movie.get().getMovieDuration(),inforCinemas);
    }
}
