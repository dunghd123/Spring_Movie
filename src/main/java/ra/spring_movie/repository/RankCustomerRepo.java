package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.RankCustomer;

import java.util.Optional;

@Repository
public interface RankCustomerRepo extends JpaRepository<RankCustomer,Integer> {
    @Query(value = "select * from rankcustomers r where r.name like ?1",nativeQuery = true)
    Optional<RankCustomer> findByName(String name);
}
