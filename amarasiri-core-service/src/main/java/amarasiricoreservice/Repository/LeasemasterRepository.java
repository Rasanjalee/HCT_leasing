package amarasiricoreservice.Repository;

import amarasiricoreservice.entity.LeaseMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeasemasterRepository extends JpaRepository<LeaseMaster,Integer> {
}
