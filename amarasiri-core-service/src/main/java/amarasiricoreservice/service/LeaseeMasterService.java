package amarasiricoreservice.service;

import amarasiricoreservice.DTO.PostLeaseDetailDto;
import amarasiricoreservice.Repository.LeaseeMasterRepository;
import amarasiricoreservice.Repository.LeasemasterRepository;
import amarasiricoreservice.Repository.VehicleRepository;
import amarasiricoreservice.entity.LeaseMaster;
import amarasiricoreservice.entity.LeaseeMaster;
import amarasiricoreservice.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LeaseeMasterService {
    @Autowired
    LeaseeMasterRepository leaseeMasterRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    LeasemasterRepository leasemasterRepository;

    public LeaseMaster saveLeaseeMaster(PostLeaseDetailDto postLeaseDetailDto){

        List<Vehicle> vehicleList =new ArrayList<>();

        if(postLeaseDetailDto!=null){
           new LeaseeMaster();
            Vehicle insertedVehicle = new Vehicle();
            LeaseMaster insertedLeaseMaster = new LeaseMaster();
            if(postLeaseDetailDto.getLeaseeMaster()!=null){
                LeaseeMaster insertedLeaseeMaster= leaseeMasterRepository.save(postLeaseDetailDto.getLeaseeMaster());
                if(postLeaseDetailDto.getVehicle()!=null){
                    postLeaseDetailDto.getVehicle().setLeaseeId(insertedLeaseeMaster.getLeaseeId());
                    insertedVehicle = vehicleRepository.save(postLeaseDetailDto.getVehicle());
                }
                if (postLeaseDetailDto.getLeaseMaster()!=null){
                    postLeaseDetailDto.getLeaseMaster().setLeaseeKey(insertedLeaseeMaster.getLeaseeId());
                    postLeaseDetailDto.getLeaseMaster().setLeaseVehicleKey(insertedVehicle.getLeaseeVehicleKey());
                    insertedLeaseMaster = leasemasterRepository.save(postLeaseDetailDto.getLeaseMaster());
                }
            }
            return  insertedLeaseMaster;
        } else {
            return null;
        }

    }
}
