package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.model.ScheduleCustom;
import ra.spring_movie.services.ScheduleService;

@RestController
@RequestMapping("/api/v1/schedule/")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("insertschedule")
    public ResponseEntity<MessageResponse> insertSchedule(@RequestBody ScheduleCustom scheduleCustom){
        return new ResponseEntity<>(scheduleService.insertSchedule(scheduleCustom), HttpStatus.CREATED);
    }
    @PutMapping("updateschedule/{id}")
    public ResponseEntity<MessageResponse> updateSchedule(@PathVariable int id,@RequestBody ScheduleCustom scheduleCustom){
        return new ResponseEntity<>(scheduleService.updateSchedule(id,scheduleCustom), HttpStatus.OK);
    }
    @DeleteMapping("deleteSchedule/{id}")
    public ResponseEntity<MessageResponse> deleteSchedule(@PathVariable int id){
        return new ResponseEntity<>(scheduleService.deleteSchedule(id), HttpStatus.OK);
    }
}
