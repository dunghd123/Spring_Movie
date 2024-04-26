package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Cinema;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepo extends JpaRepository<Cinema,Integer> {
    @Query(value = "select * from cinemas c where c.nameofcinema like ?1",nativeQuery = true)
    Optional<Cinema> findByNameOfCinema(String name);
    @Query(value = "SELECT DISTINCT c.id FROM movies m INNER JOIN schedules s ON m.id= s.movieid INNER JOIN rooms r ON r.id= s.roomid INNER JOIN cinemas c ON c.id=r.cinemaid WHERE m.name LIKE ?1",nativeQuery = true)
    List<Integer> findCinema(String moviename);
}
