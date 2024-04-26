package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Bill;

import java.util.Optional;

@Repository
public interface BillRepo  extends JpaRepository<Bill,Integer> {
    @Query(value = "select * from bills b where b.name like ?1",nativeQuery = true)
    Optional<Bill> findByName(String name);
}
