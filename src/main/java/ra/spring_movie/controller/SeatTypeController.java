package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.SeatType;
import ra.spring_movie.services.SeatTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seattype/")
public class SeatTypeController {
    @Autowired
    private SeatTypeService seatTypeService;

    @PostMapping("insertseattype")
    public ResponseEntity<MessageResponse> insertSeatType(@RequestBody SeatType seatType){
        return new ResponseEntity<>(seatTypeService.insertSeatType(seatType), HttpStatus.CREATED);
    }
    @PutMapping ("updateseattype/{id}")
    public ResponseEntity<MessageResponse> updateSeatType(@PathVariable int id,@RequestBody SeatType seatType){
        return new ResponseEntity<>(seatTypeService.updateSeatType(id,seatType), HttpStatus.OK);
    }
    @DeleteMapping("deleteseattype")
    public ResponseEntity<MessageResponse> deleteSeatType(@RequestParam String nameoftype){
        return new ResponseEntity<>(seatTypeService.deleteSeatType(nameoftype), HttpStatus.OK);
    }
    @GetMapping("viewlistseattype")
    public List<SeatType> viewList(){
        return seatTypeService.viewSeatType();
    }
}
