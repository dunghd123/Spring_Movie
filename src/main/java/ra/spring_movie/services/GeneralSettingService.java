package ra.spring_movie.services;

import lombok.Data;
import ra.spring_movie.dto.response.MessageResponse;

import ra.spring_movie.entity.GeneralSetting;

import java.util.List;
public interface GeneralSettingService {
    MessageResponse insertSetting(GeneralSetting generalSetting);
    MessageResponse updateSetting(int id, GeneralSetting generalSetting);
    MessageResponse deleteSetting(int id);
    List<GeneralSetting> viewSetting();
}
