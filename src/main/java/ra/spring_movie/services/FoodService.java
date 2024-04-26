package ra.spring_movie.services;


import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Food;

import java.util.List;

public interface FoodService {
    public MessageResponse insertFood(Food food);
    public MessageResponse updateFood(Food food);
    public MessageResponse deleteFood(int idfood);
    public List<Food> viewFood();

}
