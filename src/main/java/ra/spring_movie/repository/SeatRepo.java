package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Seat;

import java.util.Optional;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Integer> {
    @Query(value = "select * from seats s where s.line like ?1 and s.number =?2",nativeQuery = true)
    Optional<Seat> findByLineAndNumber(String line,int number);
}
