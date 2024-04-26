package ra.spring_movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ra.spring_movie.model.StatisticCustom;
import ra.spring_movie.services.impl.StatisticServiceImpl;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic/")
public class StatisticController {
    @Autowired
    private StatisticServiceImpl statisticService;

    @GetMapping("viewfood")
    public List<StatisticCustom> viewFood(@RequestParam("curDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date curDate){
        return statisticService.viewFood(curDate);
    }
}
