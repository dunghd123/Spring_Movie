package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Schedule;

import java.util.Optional;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Integer> {
    @Query(value = "select * from schedules s where s.name like ?1",nativeQuery = true)
    Optional<Schedule> findByName(String name);
}
