package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Bill;
import ra.spring_movie.model.BillCustom;
import ra.spring_movie.services.BillService;

@RestController
@RequestMapping("/api/v1/bill/")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("createbill")
    public ResponseEntity<MessageResponse> createBill(@RequestBody Bill bill){
        return new ResponseEntity<>(billService.createBill(bill), HttpStatus.CREATED);
    }
    @DeleteMapping("deletebill/{id}")
    public ResponseEntity<MessageResponse> deleteBill(@PathVariable int id){
        return new ResponseEntity<>(billService.deleteBill(id), HttpStatus.OK);
    }
    @GetMapping("inbill/{id}")
    public BillCustom printBill(@PathVariable int id){
        return billService.viewBill(id);
    }
}
