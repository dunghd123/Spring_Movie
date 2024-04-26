package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.MovieType;

import java.util.Optional;

@Repository
public interface MovieTypeRepo extends JpaRepository<MovieType,Integer> {
    @Query(value = "select * from movietypes m where m.movietypename like ?1",nativeQuery = true)
    Optional<MovieType> findByMovieTypeName(String name);
}
