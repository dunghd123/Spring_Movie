package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Cinema;

import java.util.Optional;

@Repository
public interface CinemaRepo extends JpaRepository<Cinema,Integer> {
    @Query(value = "select * from cinemas c where c.nameofcinema like ?1",nativeQuery = true)
    Optional<Cinema> findByNameOfCinema(String name);
}
