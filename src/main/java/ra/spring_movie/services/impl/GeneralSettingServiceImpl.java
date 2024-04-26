package ra.spring_movie.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.entity.GeneralSetting;
import ra.spring_movie.repository.GeneralSettingRepo;
import ra.spring_movie.services.GeneralSettingService;

import java.util.List;
import java.util.Optional;

@Service
public class GeneralSettingServiceImpl implements GeneralSettingService {
    @Autowired
    private GeneralSettingRepo generalSettingRepo;
    @Override
    public MessageResponse insertSetting(GeneralSetting generalSetting) {
        generalSettingRepo.save(generalSetting);
        return MessageResponse.builder().message("Insert Successfully!!!").build();
    }

    @Override
    public MessageResponse updateSetting(int id, GeneralSetting generalSetting) {
        Optional<GeneralSetting> generalSettingCur= generalSettingRepo.findById(id);
        if(generalSettingCur.isEmpty()){
            return MessageResponse.builder().message("Invalid Setting!!!").build();
        }else {
            generalSettingCur.get().setBreakTime(generalSetting.getBreakTime());
            generalSettingCur.get().setBusinessHours(generalSetting.getBusinessHours());
            generalSettingCur.get().setCloseTime(generalSetting.getCloseTime());
            generalSettingCur.get().setFixedTicketPrice(generalSetting.getFixedTicketPrice());
            generalSettingCur.get().setPercentDay(generalSetting.getPercentDay());
            generalSettingCur.get().setPercentWeekend(generalSetting.getPercentWeekend());
            generalSettingCur.get().setTimeBeginToChange(generalSetting.getTimeBeginToChange());
            generalSettingRepo.save(generalSettingCur.get());
            return MessageResponse.builder().message("Update Successfully!!!").build();
        }
    }

    @Override
    public MessageResponse deleteSetting(int id) {
        Optional<GeneralSetting> generalSettingCur= generalSettingRepo.findById(id);
        if(generalSettingCur.isEmpty()){
            return MessageResponse.builder().message("Invalid Setting!!!").build();
        }else {
            generalSettingRepo.deleteById(id);
            return MessageResponse.builder().message("Delete Successfully!!!").build();
        }
    }

    @Override
    public List<GeneralSetting> viewSetting() {
        return generalSettingRepo.findAll();
    }
}