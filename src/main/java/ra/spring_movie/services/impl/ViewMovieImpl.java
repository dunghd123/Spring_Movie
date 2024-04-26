package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.ListMovieResponse;

import ra.spring_movie.entity.Movie;
import ra.spring_movie.entity.Schedule;
import ra.spring_movie.entity.Ticket;
import ra.spring_movie.repository.*;
import ra.spring_movie.services.ViewMovie;

import java.util.*;


@Service
public class ViewMovieImpl implements ViewMovie {
    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private TicketRepo ticketRepo;

    private static List<ListMovieResponse> sortList(List<ListMovieResponse> list){
        Collections.sort(list, (o1, o2) -> Integer.compare(o2.getNumberOfTicket(), o1.getNumberOfTicket()));
        return list;
    }
    @Override
    public List<ListMovieResponse> printList() {
        List<ListMovieResponse> listMovieResponses= new ArrayList<>();
        for(Movie movie: movieRepo.findAll()){
            int count=0;
            for(Schedule schedule: scheduleRepo.findAll()){
                if(schedule.getMovie().getId()==movie.getId()){
                    for(Ticket ticket: ticketRepo.findAll()){
                        if(ticket.getSchedule().getId()==schedule.getId()){
                            count++;
                        }
                    }
                }
            }
            listMovieResponses.add(new ListMovieResponse(movie.getName(),count));
        }
        return sortList(listMovieResponses);
    }
}

