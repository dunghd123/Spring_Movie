package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.entity.Bill;
import ra.spring_movie.entity.BillFood;
import ra.spring_movie.entity.Food;
import ra.spring_movie.model.StatisticCustom;
import ra.spring_movie.repository.BillFoodRepo;
import ra.spring_movie.repository.BillRepo;
import ra.spring_movie.repository.FoodRepo;
import ra.spring_movie.services.StatisticService;

import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired private BillFoodRepo billFoodRepo;
    @Autowired private FoodRepo foodRepo;
    @Autowired private BillRepo billRepo;

    private static Date calculatePreviousDate(Date currentDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTime();
    }
    @Override
    public List<StatisticCustom> viewFood(Date currentDate) {
        List<StatisticCustom> list= new ArrayList<>();
        Date beforeDate= calculatePreviousDate(currentDate,7);
        for(Food food: foodRepo.findAll()){
            int count=0;
            for(BillFood billFood: billFoodRepo.findAll()){
                if(billFood.getFood().getId()== food.getId()){
                    for(Bill bill: billRepo.findAll()){
                        if(billFood.getBill().getId()==bill.getId()){
                            if(bill.getUpdateTime().after(beforeDate)
                                    && bill.getUpdateTime().before(currentDate)
                                    && bill.isIsActive()){
                                count+=billFood.getQuantity();
                            }
                        }
                    }
                }
            }
            list.add(new StatisticCustom(food.getNameOfFood(),count));
        }
        return sortList(list);
    }
    private static List<StatisticCustom> sortList(List<StatisticCustom> list){
        double sum=0;
        for(StatisticCustom st:list){
            sum+=st.getQuantity();
        }
        double averageCount= sum / list.size();
        list.removeIf(st -> st.getQuantity() < averageCount);
        list.sort((o1, o2) -> o2.getQuantity() - o1.getQuantity());
        return list;
    }
}
