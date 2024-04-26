package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;

import ra.spring_movie.entity.GeneralSetting;
import ra.spring_movie.services.impl.GeneralSettingServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/v1/setting/")
public class GeneralSettingController {
    @Autowired
    private GeneralSettingServiceImpl generalSettingService;
    @PostMapping("insertsetting")
    public ResponseEntity<MessageResponse> insertSetting(@RequestBody GeneralSetting generalSetting){
        return new ResponseEntity<>(generalSettingService.insertSetting(generalSetting), HttpStatus.CREATED);
    }
    @PutMapping("updatesetting/{id}")
    public ResponseEntity<MessageResponse> updateSetting(@PathVariable int id, @RequestBody GeneralSetting generalSetting){
        return new ResponseEntity<>(generalSettingService.updateSetting(id,generalSetting), HttpStatus.OK);
    }
    @DeleteMapping("deletesetting/{id}")
    public ResponseEntity<MessageResponse> deleteSetting(@PathVariable int id){
        return new ResponseEntity<>(generalSettingService.deleteSetting(id), HttpStatus.OK);
    }
    @GetMapping("viewlistsetting")
    public List<GeneralSetting> viewSetting(){
        return generalSettingService.viewSetting();
    }
}
