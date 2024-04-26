package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.BillFood;
import ra.spring_movie.model.BillFoodCustom;

public interface BillFoodService {
    MessageResponse insertBillFood(BillFoodCustom billFood);
    MessageResponse updateBillFood(int id,BillFoodCustom billFood);
    MessageResponse deleteBillFood(int id);
}
