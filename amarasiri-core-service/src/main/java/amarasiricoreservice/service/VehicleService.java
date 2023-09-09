package amarasiricoreservice.service;

import amarasiricoreservice.Repository.VehicleRepository;
import amarasiricoreservice.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getVehicles(Integer vehicleId,List<Integer> vehicleIds){
        List<Vehicle> vehicleList = new ArrayList<>();

        if (vehicleId != null){
            vehicleList.addAll(vehicleRepository.findByLeaseeVehicleKey(vehicleId));
        } else if (vehicleIds != null) {
//            vehicleList.addAll(vehicleRepository.findByVehicleIds(vehicleIds));
        }

        return vehicleList;
    }
}
