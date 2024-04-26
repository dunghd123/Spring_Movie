package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Room;

import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room,Integer> {
    @Query(value = "select * from rooms r where r.name like ?1",nativeQuery = true)
    Optional<Room> findByName(String name);
}
