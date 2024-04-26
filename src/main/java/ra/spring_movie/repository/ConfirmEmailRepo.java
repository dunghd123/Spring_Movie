package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.ConfirmEmail;

import java.util.Optional;

@Repository
public interface ConfirmEmailRepo extends JpaRepository<ConfirmEmail,Integer> {
    @Query(value = "SELECT * FROM confirmemails u WHERE u.userid = ?1",nativeQuery = true)
    Optional<ConfirmEmail> findByUser(int  userid);
    @Query(value = "SELECT * FROM confirmemails u WHERE u.confirmcode like ?1",nativeQuery = true)
    Optional<ConfirmEmail> findByConfirmCode(String  code);

}
