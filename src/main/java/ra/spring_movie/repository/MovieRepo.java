package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Movie;
import ra.spring_movie.entity.MovieType;

import java.util.Optional;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Integer> {
    @Query(value = "select * from movies m where m.name like ?1",nativeQuery = true)
    Optional<Movie> findByName(String name);
}
