package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Food;

import java.util.Optional;

@Repository
public interface FoodRepo extends JpaRepository<Food,Integer> {
    @Query(value = "select * from foods f where f.nameoffood like ?1",nativeQuery = true)
    Optional<Food> findByNameOfFood(String nameOfFood);
}
