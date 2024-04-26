package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.RankCustomer;
import ra.spring_movie.services.impl.RankCustomerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rankcustomer/")
public class RankCustomerController {
    @Autowired
    private RankCustomerServiceImpl rankService;
    @PostMapping("insertrank")
    public ResponseEntity<MessageResponse> insertRank(@RequestBody RankCustomer rankCustomer){
        return new ResponseEntity<>(rankService.insertRank(rankCustomer), HttpStatus.CREATED);
    }
    @PutMapping("updaterank/{id}")
    public ResponseEntity<MessageResponse> updateRank(@PathVariable int id, @RequestBody RankCustomer rankCustomer){
        return new ResponseEntity<>(rankService.updateRank(id,rankCustomer), HttpStatus.OK);
    }
    @DeleteMapping("deleterank/{id}")
    public ResponseEntity<MessageResponse> deleteRank(@PathVariable int id){
        return new ResponseEntity<>(rankService.deleteRank(id), HttpStatus.OK);
    }
    @GetMapping("viewlistrank")
    public List<RankCustomer> viewRank(){
        return rankService.viewRank();
    }
}
