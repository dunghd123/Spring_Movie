package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ra.spring_movie.model.InforMovie;
import ra.spring_movie.services.BookingTicketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking/")
public class BookingController {
    @Autowired
    private BookingTicketService bookingTicketServicel;

    @GetMapping("view_list_cinema")
    public List<Integer> viewCinema(@RequestParam String movieName){
        return bookingTicketServicel.viewCinema(movieName);
    }
    @GetMapping("view_list")
    public InforMovie viewInforMovieSelected(@RequestParam String movieName){
        return bookingTicketServicel.getData(movieName);
    }
}
