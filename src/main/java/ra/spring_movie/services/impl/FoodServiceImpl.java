package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.BillFood;
import ra.spring_movie.entity.Food;
import ra.spring_movie.repository.BillFoodRepo;
import ra.spring_movie.repository.FoodRepo;
import ra.spring_movie.services.FoodService;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private BillFoodRepo billFoodRepo;
    @Override
    public MessageResponse insertFood(Food food) {
        food.setIsActive(true);
        foodRepo.save(food);
        return MessageResponse.builder().message("insert food successfully!!").build();
    }

    @Override
    public MessageResponse updateFood(Food food) {
        Optional<Food> foodCurrent= foodRepo.findById(food.getId());
        if(foodCurrent.isEmpty()){
            return MessageResponse.builder().message("Food does not exist!!!").build();
        }else {
            Food foodCur= foodCurrent.get();
            foodCur.setNameOfFood(food.getNameOfFood());
            foodCur.setImage(food.getImage());
            foodCur.setDescription(food.getDescription());
            foodCur.setPrice(food.getPrice());
            foodCur.setIsActive(true);
            foodRepo.save(foodCur);
            return MessageResponse.builder().message("update food "+foodCurrent.get().getNameOfFood()+" successfully!!").build();
        }
    }

    @Override
    public MessageResponse deleteFood(int idfood) {
        Optional<Food> foodCurrent= foodRepo.findById(idfood);
        if(foodCurrent.isEmpty()){
            return MessageResponse.builder().message("Food does not exist!!!").build();
        }else {
           for(BillFood bf: billFoodRepo.findAll()){
               if(bf.getFood().getId()==idfood){
                   billFoodRepo.delete(bf);
               }
           }
           foodCurrent.get().setIsActive(false);
           return MessageResponse.builder().message("Delete ("+foodCurrent.get().getId()+": "+foodCurrent.get().getNameOfFood()+") successfully!!").build();
        }
    }

    @Override
    public List<Food> viewFood() {
        return foodRepo.findAll();
    }
}
