package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.Promotion;

import java.util.List;

public interface PromotionService {
    MessageResponse insertPro(Promotion promotion);
    MessageResponse updatePro(int id,Promotion Promotion);
    MessageResponse deletePro(int id);
    List<Promotion> viewPro();
}
