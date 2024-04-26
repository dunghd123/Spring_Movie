package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.model.BankCustom;
import ra.spring_movie.services.impl.VnPayServiceImpl;

@RestController
@RequestMapping("/api/v1/vnpay/")
public class VnPayController {
    @Autowired
    private  VnPayServiceImpl vnPayService;

    @PostMapping("payment/{billid}")
    public ResponseEntity<MessageResponse> billPayment(@RequestBody BankCustom bankCustom, @PathVariable int billid){
        return new ResponseEntity<>(vnPayService.payment(bankCustom,billid), HttpStatus.OK);
    }
}
