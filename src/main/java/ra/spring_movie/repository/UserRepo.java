package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query(value = "select * from users s where s.username like ?1",nativeQuery = true)
    Optional<User> findByUsername(String username);
}
