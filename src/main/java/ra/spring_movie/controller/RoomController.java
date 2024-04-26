package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Room;

import ra.spring_movie.services.impl.RoomServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room/")
public class RoomController {
    @Autowired
    private RoomServiceImpl roomService;

    @PostMapping("insertroom")
    public ResponseEntity<MessageResponse> insertRoom(@RequestBody Room room){
        return new ResponseEntity<>(roomService.insertRoom(room), HttpStatus.CREATED);
    }
    @PutMapping("updateroom")
    public ResponseEntity<MessageResponse> updateRoom(@RequestBody Room room){
        return new ResponseEntity<>(roomService.updateRoom(room), HttpStatus.OK);
    }
    @DeleteMapping("deleteroom")
    public ResponseEntity<MessageResponse> deleteRoom(@RequestParam int roomid){
        return new ResponseEntity<>(roomService.deleteRoom(roomid), HttpStatus.OK);
    }
    @GetMapping("viewroom")
    public List<Room> viewRoom(){
        return roomService.viewRoom();
    }
}
