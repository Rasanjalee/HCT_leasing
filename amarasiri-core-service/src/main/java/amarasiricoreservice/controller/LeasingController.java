package amarasiricoreservice.controller;

import amarasiricoreservice.DTO.PostLeaseDetailDto;
import amarasiricoreservice.entity.LeaseMaster;
import amarasiricoreservice.entity.LeaseeMaster;
import amarasiricoreservice.entity.Vehicle;
import amarasiricoreservice.service.LeaseeMasterService;
import amarasiricoreservice.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leasing")
public class LeasingController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private LeaseeMasterService leaseeMasterService;

    @RequestMapping(value = "/vehicles",method = RequestMethod.GET)
    @Operation(summary = "Get all the vehicles",description = "Return Vehicle Details",tags = {"Vehicle"})
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "successfull opertion"),
            @ApiResponse(responseCode = "400",description = "Vehcile record not found")
    })
    public List<Vehicle> getAllVehcles(@RequestParam(name = "vehicle-id",value = "vehicle-id") int vehicleId,
                                       @RequestParam(name = "vehicle-id-list", value = "vehicle-id-list",required = false) List<Integer> vehicleIdList) {

        try {

            List<Vehicle> vehicleList = vehicleService.getVehicles(vehicleId, vehicleIdList);

            if (!vehicleList.isEmpty()) {
                return vehicleList;
            }
            else {
                throw new RuntimeException();
            }
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/vehicles",method = RequestMethod.POST)
    @Operation(summary = "Save",description = "Return saved Details",tags = {"LeaseeMaster"})
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "successfull opertion"),
            @ApiResponse(responseCode = "400",description = "Vehcile record not found")
    })
    public LeaseMaster saveLeaseeMaster(@RequestBody PostLeaseDetailDto postLeaseDetailDto){
        return leaseeMasterService.saveLeaseeMaster(postLeaseDetailDto);
    }
}
