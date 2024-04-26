package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {
    @Query(value = "select * from refreshtokens r where r.userid=?1",nativeQuery = true)
    Optional<RefreshToken> findByUserId(int userid);
}
