package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.model.BillFoodCustom;
import ra.spring_movie.services.impl.BillFoodServiceImpl;

@RestController
@RequestMapping("/api/v1/billfood/")
public class BillFoodController {
    @Autowired
    private BillFoodServiceImpl billFoodService;
    @PostMapping("insertbillfood")
    public ResponseEntity<MessageResponse> insertBillFood(@RequestBody BillFoodCustom billFoodCustom){
        return new ResponseEntity<>(billFoodService.insertBillFood(billFoodCustom),HttpStatus.CREATED);
    }
    @PutMapping("updatebillfood/{id}")
    public ResponseEntity<MessageResponse> updateBillFood(@PathVariable int id,@RequestBody BillFoodCustom billFoodCustom){
        return new ResponseEntity<>(billFoodService.updateBillFood(id,billFoodCustom),HttpStatus.OK);
    }
    @DeleteMapping("deletebillfood/{id}")
    public ResponseEntity<MessageResponse> deleteBillFood(@PathVariable int id){
        return new ResponseEntity<>(billFoodService.deleteBillFood(id),HttpStatus.OK);
    }
}
