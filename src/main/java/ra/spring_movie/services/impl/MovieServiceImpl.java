package ra.spring_movie.services.impl;



import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.*;

import ra.spring_movie.helper.DateTimeHelper;
import ra.spring_movie.model.MovieCustom;
import ra.spring_movie.repository.*;

import ra.spring_movie.services.MovieService;

import java.util.List;
import java.util.Optional;

import static ra.spring_movie.helper.DateTimeHelper.subtractHoursFromDate;


@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BillTicketRepo billTicketRepo;
    @Autowired
    private MovieTypeRepo movieTypeRepo;
    @Autowired
    private RateRepo rateRepo;
    @Override
    public MessageResponse insertMovie(MovieCustom movie) {
        Optional<MovieType> movieType= movieTypeRepo.findById(movie.getMovieTypeId());
        Optional<Rate> rate= rateRepo.findById(movie.getRateId());

        if(movieType.isEmpty()|| rate.isEmpty()){
            return MessageResponse.builder().message("Insert movie:"+movie.getName()+" failed!!!").build();
        }else {
            Movie newMovie= new Movie(movie.getMovieDuration(),subtractHoursFromDate(movie.getEndTime()) ,subtractHoursFromDate(movie.getPremiereDate()), movie.getDescription(),
                    movie.getDirector(), movie.getImage(), movie.getHeroImage(), movie.getLanguage(), movie.getName(), movie.getTrailer(), movieType.get() ,rate.get());
            newMovie.setIsActive(true);
            movieRepo.save(newMovie);
            return MessageResponse.builder().message("Insert movie:"+movie.getName()+" successfully!!!").build();
        }
    }
    @Override
    public MessageResponse updateMovie(int id,MovieCustom movie) {
        Optional<MovieType> movieType= movieTypeRepo.findById(movie.getMovieTypeId());
        Optional<Rate> rate= rateRepo.findById(movie.getRateId());
        Optional<Movie> optionalMovie= movieRepo.findById(id);
        if(optionalMovie.isEmpty()){
            return MessageResponse.builder().message("movie does not exist!!!").build();
        }else {
            if(movieType.isEmpty()|| rate.isEmpty()) {
                return MessageResponse.builder().message("movieType or Rate does not exist!!!").build();
            }
            else {
                Movie movieCur= optionalMovie.get();
                movieCur.setName(movie.getName());
                movieCur.setMovieDuration(movie.getMovieDuration());
                movieCur.setDirector(movie.getDirector());
                movieCur.setDescription(movie.getDescription());
                movieCur.setMovieType(movieType.get());
                movieCur.setEndTime(movie.getEndTime());
                movieCur.setImage(movie.getImage());
                movieCur.setPremiereDate(movie.getPremiereDate());
                movieCur.setLanguage(movie.getLanguage());
                movieCur.setHeroImage(movie.getHeroImage());
                movieCur.setRate(rate.get());
                movieCur.setTrailer(movie.getTrailer());
                movieRepo.save(movieCur);
                return MessageResponse.builder().message("Update movie:"+movie.getName()+" successfully!!!").build();
            }
        }
    }
    @Override
    public MessageResponse deleteMovie(int movieid) {
        Optional<Movie> movie= movieRepo.findById(movieid);
        if(movie.isEmpty()){
            return MessageResponse.builder().message("movie does not exist!!!").build();
        }else {
            //xoa movie trong schedule
            for(Schedule schedule: scheduleRepo.findAll()){
                if(schedule.getMovie().getId()==movieid){
                    //xoa ticket cua schedule
                    for(Ticket ticket: ticketRepo.findAll()){
                        if(ticket.getSchedule().getId()==schedule.getId()){
                            //xoa billticket cua ticket
                            for(BillTicket billTicket: billTicketRepo.findAll()){
                                if(billTicket.getTicket().getId()== ticket.getId()){
                                    billTicketRepo.delete(billTicket);
                                }
                            }
                            ticket.setIsActive(false);
                            ticketRepo.save(ticket);
                        }
                    }
                    schedule.setIsActive(false);
                    scheduleRepo.save(schedule);
                }
            }
            movie.get().setIsActive(false);
            movieRepo.save(movie.get());
            return MessageResponse.builder().message("Delete movie successfully!!!").build();
        }
    }
    @Override
    public List<Movie> viewMovie() {
        return movieRepo.findAll();
    }
}
