package ra.spring_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.spring_movie.entity.GeneralSetting;
@Repository
public interface GeneralSettingRepo extends JpaRepository<GeneralSetting,Integer> {
}
