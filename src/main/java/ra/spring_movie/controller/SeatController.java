package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Seat;
import ra.spring_movie.model.SeatCustom;
import ra.spring_movie.services.impl.SeatServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seat/")
public class SeatController {
    @Autowired
    private SeatServiceImpl seatService;

    @PostMapping("insertseat")
    public ResponseEntity<MessageResponse> insertSeat(@RequestBody SeatCustom seatCustom){
        return new ResponseEntity<>(seatService.insertSeat(seatCustom), HttpStatus.CREATED);
    }
    @PutMapping("updateseat/{id}")
    public ResponseEntity<MessageResponse> updateSeat(@PathVariable int id,@RequestBody SeatCustom seat){
        return new ResponseEntity<>(seatService.updateSeat(id,seat), HttpStatus.OK);
    }
    @DeleteMapping("deleteseat/{id}")
    public ResponseEntity<MessageResponse> deleteSeat(@PathVariable int id){
        return new ResponseEntity<>(seatService.deleteSeat(id), HttpStatus.OK);
    }
    @GetMapping("viewseat")
    public List<Seat> viewSeat(){
        return seatService.viewSeat();
    }
}
