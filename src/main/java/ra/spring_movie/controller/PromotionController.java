package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Promotion;
import ra.spring_movie.services.impl.PromotionServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion/")
public class PromotionController {
    @Autowired
    private PromotionServiceImpl promotionService;

    @PostMapping("insertpro")
    public ResponseEntity<MessageResponse> insertPro(@RequestBody Promotion promotion){
        return new ResponseEntity<>(promotionService.insertPro(promotion), HttpStatus.CREATED);
    }
    @PutMapping("updatepro/{id}")
    public ResponseEntity<MessageResponse> updatePro(@PathVariable int id,@RequestBody Promotion promotion){
        return new ResponseEntity<>(promotionService.updatePro(id,promotion), HttpStatus.OK);
    }
    @DeleteMapping("deletepro/{id}")
    public ResponseEntity<MessageResponse> deletePro(@PathVariable int id){
        return new ResponseEntity<>(promotionService.deletePro(id), HttpStatus.OK);
    }
    @GetMapping("viewpromotion")
    public List<Promotion> viewListPro(){
        return promotionService.viewPro();
    }
}
