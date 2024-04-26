package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.SeatStatus;
import ra.spring_movie.services.impl.SeatStatusServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seatstatus/")
public class SeatStatusController {
    @Autowired
    private SeatStatusServiceImpl seatStatusService;

    @PostMapping("insertseatstatus")
    public ResponseEntity<MessageResponse> insertSeatStatus(@RequestBody SeatStatus status){
        return new ResponseEntity<>(seatStatusService.insertSeatStatus(status), HttpStatus.CREATED);
    }
    @PutMapping("updateseatstatus/{id}")
    public ResponseEntity<MessageResponse> updateSeatStatus(@PathVariable int id,@RequestBody SeatStatus seatStatus){
        return new ResponseEntity<>(seatStatusService.updateSeatStatus(id,seatStatus), HttpStatus.OK);
    }
    @DeleteMapping("deleteseatstatus/{id}")
    public ResponseEntity<MessageResponse> deleteSeatType(@PathVariable int id){
        return new ResponseEntity<>(seatStatusService.deleteSeatStatus(id), HttpStatus.OK);
    }
    @GetMapping("viewlistseatstatus")
    public List<SeatStatus> viewList(){
        return seatStatusService.viewSeatStatus();
    }
}
