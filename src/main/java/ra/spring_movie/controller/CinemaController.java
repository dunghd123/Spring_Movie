package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Cinema;
import ra.spring_movie.services.CinemaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cinema/")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping("insertcinema")
    public ResponseEntity<MessageResponse> insertCinema(@RequestBody Cinema cinema){
        return new ResponseEntity<>(cinemaService.insertCinema(cinema), HttpStatus.CREATED);
    }
    @PutMapping("updatecinemainfor")
    public ResponseEntity<MessageResponse> updateCinema(@RequestBody Cinema cinema){
        return new ResponseEntity<>(cinemaService.updateCinema(cinema), HttpStatus.OK);
    }
    @DeleteMapping("deletecinema")
    public ResponseEntity<MessageResponse> deleteCinema(@RequestParam int cinemaid){
        return new ResponseEntity<>(cinemaService.deleteCinema(cinemaid), HttpStatus.OK);
    }
    @GetMapping("displaycinema")
    public List<Cinema> displayCinema(){
        return cinemaService.viewCinema();
    }
}
