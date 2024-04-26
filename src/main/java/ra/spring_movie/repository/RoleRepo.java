package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.Role;
import ra.spring_movie.enums.RoleEnums;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    @Query(value = "select * from roles r where r.rolename like ?1",nativeQuery = true)
    Optional<Role> findByRoleName(String  rolename);
}
