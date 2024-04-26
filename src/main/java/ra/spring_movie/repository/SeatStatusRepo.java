package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ra.spring_movie.entity.SeatStatus;

import java.util.Optional;

@Repository
public interface SeatStatusRepo extends JpaRepository<SeatStatus,Integer> {
    @Query(value = "select * from seatstatus s where s.code like ?1",nativeQuery = true)
    Optional<SeatStatus> findByCode(String code);
}
