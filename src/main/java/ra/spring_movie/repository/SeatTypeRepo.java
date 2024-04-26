package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.SeatType;

import java.util.Optional;

@Repository
public interface SeatTypeRepo extends JpaRepository<SeatType,Integer> {
    @Query(value = "select * from seattypes s where s.nametype like ?1",nativeQuery = true)
    Optional<SeatType> findByNameType(String nameType);
}
