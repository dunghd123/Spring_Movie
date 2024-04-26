package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;
import ra.spring_movie.model.ScheduleCustom;

public interface ScheduleService {

    MessageResponse insertSchedule(ScheduleCustom scheduleCustom);
    MessageResponse updateSchedule(int id, ScheduleCustom scheduleCustom);
    MessageResponse deleteSchedule(int id);

}
