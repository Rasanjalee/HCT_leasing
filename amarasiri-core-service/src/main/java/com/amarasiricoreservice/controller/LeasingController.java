package com.amarasiricoreservice.controller;

import com.amarasiricoreservice.DTO.CalculatedLeaseDto;
import com.amarasiricoreservice.DTO.PostLeaseDetailDto;
import com.amarasiricoreservice.config.AccessToken;
import com.amarasiricoreservice.entity.LeaseMaster;
import com.amarasiricoreservice.entity.Vehicle;
import com.amarasiricoreservice.service.DocumentsUploadService;
import com.amarasiricoreservice.service.LeaseeMasterService;
import com.amarasiricoreservice.service.MainService;
import com.amarasiricoreservice.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("/leasing")
public class LeasingController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private LeaseeMasterService leaseeMasterService;

    @Autowired
    private DocumentsUploadService documentsUploadService;

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/vehicles",method = RequestMethod.GET)
    @Operation(summary = "Get all the vehicles",description = "Return Vehicle Details",tags = {"Vehicle"})
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "successfull opertion"),
            @ApiResponse(responseCode = "400",description = "Vehcile record not found")
    })
    public Vehicle getAllVehcles(@RequestParam(name = "vehicle-id",value = "vehicle-id") int vehicleId) {

        try {

            Vehicle vehicleList = vehicleService.getVehicles(vehicleId);

            if (vehicleList!=null) {
                return vehicleList;
            }
            else {
                throw new RuntimeException();
            }
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
//    @PreAuthorize("hasRole('Administrator')")
    @Operation(summary = "Save",description = "Return saved Details",tags = {"LeaseeMaster"})
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "successfull opertion"),
            @ApiResponse(responseCode = "400",description = "Vehcile record not found")
    })
    public LeaseMaster createNewLease(@RequestBody PostLeaseDetailDto postLeaseDetailDto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        return mainService.saveLease(postLeaseDetailDto);
    }

    @RequestMapping(value = "/document-upload", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public String uploadDocumentsRegardingLease(@RequestParam("image") MultipartFile image) throws IOException {
        String imageUrl = null;
        if( !image.isEmpty() )
        {
            imageUrl = documentsUploadService.uploadLeaseDocuments( image, "D:/image", "D:/image" );
        }
        return imageUrl;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('create_profile')")
    public  List<PostLeaseDetailDto> getLeasingDetails (
            @RequestParam("open-status") Integer openStatus){
        return mainService.getLeaseDetails(openStatus);
    }


    @RequestMapping(value = "/calculate-installment",method = RequestMethod.GET)
    public CalculatedLeaseDto manageLeasePayment(
            @RequestParam("lease-id") Integer leaseId,
            @RequestParam("payment-received-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date payemtReceivedDate){
    return mainService.calculateInterest(leaseId,payemtReceivedDate);
    }

    @RequestMapping(value = "/make-payment",method = RequestMethod.PUT)
    public ResponseEntity makePayment(
            @RequestParam("lease-id") Integer leaseId,
            @RequestParam("interest") Double interest,
            @RequestParam("payment-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate,
            @RequestParam("user-id") Integer userId,
            @RequestParam("installment") Double installment,
            @RequestParam("penalty-duration") Integer penaltyDuration,
            @RequestParam("penalty-amount") Double penaltyAmount){

        LeaseMaster lease = mainService.makePayment(leaseId,installment,interest,paymentDate,userId,penaltyDuration,penaltyAmount);
        if(lease != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


}
