package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.RankCustomer;
@Repository
public interface RankCustomerRepo extends JpaRepository<RankCustomer,Integer> {
}
