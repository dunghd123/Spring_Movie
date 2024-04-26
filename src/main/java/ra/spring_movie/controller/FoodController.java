package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Food;
import ra.spring_movie.services.FoodService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food/")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping("insertfood")
    public ResponseEntity<MessageResponse> insertFood(@RequestBody Food food){
        return new ResponseEntity<>(foodService.insertFood(food), HttpStatus.OK);
    }
    @PutMapping("updatefood")
    public ResponseEntity<MessageResponse> updateFood(@RequestBody Food food){
        return new ResponseEntity<>(foodService.updateFood(food), HttpStatus.OK);
    }
    @DeleteMapping("deletefood")
    public ResponseEntity<MessageResponse> deleteFood(@RequestParam int idfood){
        return new ResponseEntity<>(foodService.deleteFood(idfood), HttpStatus.OK);
    }
    @GetMapping("displayfood")
    public List<Food> viewFood(){
        return foodService.viewFood();
    }
}
