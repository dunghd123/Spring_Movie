package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.BillStatus;

import java.util.Optional;

@Repository
public interface BillStatusRepo extends JpaRepository<BillStatus,Integer> {
    @Query(value = "select * from billstatus b where b.name like ?1",nativeQuery = true)
    Optional<BillStatus> findByName(String name);
}
