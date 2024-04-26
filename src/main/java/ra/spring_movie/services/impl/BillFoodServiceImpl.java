package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.BillFood;
import ra.spring_movie.entity.Food;
import ra.spring_movie.model.BillFoodCustom;
import ra.spring_movie.repository.BillFoodRepo;
import ra.spring_movie.repository.BillRepo;
import ra.spring_movie.repository.FoodRepo;
import ra.spring_movie.services.BillFoodService;

import java.util.Optional;

@Service
public class BillFoodServiceImpl implements BillFoodService {
    @Autowired
    private BillFoodRepo billFoodRepo;
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private BillRepo billRepo;
    @Override
    public MessageResponse insertBillFood(BillFoodCustom billFoodCustom) {
        Optional<Food> food= foodRepo.findByNameOfFood(billFoodCustom.getFoodName());
        if(food.isPresent()){
            BillFood billFood= new BillFood();
            billFood.setFood(food.get());
            billFood.setQuantity(billFoodCustom.getQuantity());
            billFoodRepo.save(billFood);
            return MessageResponse.builder().message("Insert billfood successfully!!!").build();
        }
        else {
            return MessageResponse.builder().message("Food does not exist").build();
        }
    }
    @Override
    public MessageResponse updateBillFood(int id, BillFoodCustom billFoodCustom) {
        Optional<BillFood> billFood= billFoodRepo.findById(id);
        if(billFood.isEmpty()){
            return MessageResponse.builder().message("Billfood invalid!!!").build();
        }else {
            Optional<Food> food= foodRepo.findByNameOfFood(billFoodCustom.getFoodName());
            if(food.isEmpty()){
                return MessageResponse.builder().message("Food does not exist").build();
            }else {
                billFood.get().setQuantity(billFoodCustom.getQuantity());
                billFood.get().setFood(food.get());
                billFoodRepo.save(billFood.get());
                return MessageResponse.builder().message("Update billfood successfully!!!").build();
            }
        }
    }
    @Override
    public MessageResponse deleteBillFood(int id) {
        Optional<BillFood> billFood= billFoodRepo.findById(id);
        if(billFood.isEmpty()){
            return MessageResponse.builder().message("Billfood invalid!!!").build();
        }else {
            deleteBillFood(id);
            return MessageResponse.builder().message("Delete billfood successfully!!!").build();
        }
    }
}
