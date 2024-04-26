package ra.spring_movie.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeHelper {
    public static Date subtractHoursFromDate(Date startDate) {
        // Chuyển đổi Date sang LocalDateTime
        LocalDateTime localDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // Trừ đi số giờ cần trừ
        LocalDateTime newDateTime = localDateTime.minusHours(7);
        // Chuyển đổi LocalDateTime mới thành Date
        return Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
