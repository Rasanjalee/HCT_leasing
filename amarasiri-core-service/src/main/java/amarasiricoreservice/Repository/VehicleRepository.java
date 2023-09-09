package amarasiricoreservice.Repository;

import amarasiricoreservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {

    List<Vehicle> findByLeaseeVehicleKey(Integer vehicleId);

//    @Query("select v from Vehicle v where v.Leaseevehiclekey in (:vehicleIds)")
//    List<Vehicle> findByVehicleIds(@Param("vehicleIds") List<Integer> vehicleIds);
}
