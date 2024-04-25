package com.amarasiricoreservice.service;

import com.amarasiricoreservice.DTO.PostLeaseDetailDto;
import com.amarasiricoreservice.Repository.LeaseeMasterRepository;
import com.amarasiricoreservice.Repository.LeasemasterRepository;
import com.amarasiricoreservice.Repository.VehicleRepository;
import com.amarasiricoreservice.entity.LeaseMaster;
import com.amarasiricoreservice.entity.LeaseeMaster;
import com.amarasiricoreservice.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public List<LeaseMaster> getLeasingDetails(Integer openStatus) {
        if (openStatus == 2) {
            return leasemasterRepository.findAll();
        } else {
            return leasemasterRepository.findByIsLeaseClosed(openStatus);
        }
    }

    public LeaseeMaster getLeaseeDetails(int LeaseeId){
        return leaseeMasterRepository.findByLeaseeId(LeaseeId);
    }

    public LeaseMaster getLeaseDetail(int leaseId){

        Optional<LeaseMaster> leaseMaster = leasemasterRepository.findById(leaseId);
        return leaseMaster.orElse(null);
    }


    public LeaseMaster updatePayment(Integer leaseId, LocalDate nextPaymentDate, Double remainingCapital, Double remainingLeaseAmount, Double totalLeaseCost, Double totalInterestCollected,
                                     Double remainingTotalLeaseCostForLastPayment,
                                     Double remaianigTotalInterestForLastPayment,
                                     Integer duration,
                                     Integer isPaymentOutDated,
                                     Double currentOutStandingBalance,
                                     Double nextPaymentDateOutStandingBalance,
                                     Integer lastPaidInstallmentIndex,
                                     Integer closedUserKey,
                                     Double closingAmount,
                                     Double closingInterest,
                                     Double closingCalculatedInterestAmount,
                                     Double closingCapitalAmount,
                                     Date closedDateTime) {

        Date nextPayDate = java.util.Date.from(nextPaymentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        LeaseMaster lease = getLeaseDetail(leaseId);

        lease.setRemainingCapial(remainingCapital);
        lease.setNextPaymentDate(nextPayDate);
        lease.setRemainingLeaseAmount(remainingLeaseAmount);
        lease.setTotalLeaseCost(totalLeaseCost);
        lease.setTotalInterestCollected(totalInterestCollected);
        lease.setRemainingTotalLeaseCostForLastPayment(remainingTotalLeaseCostForLastPayment);
        lease.setRemainingTotalInterestForLastPayment(remaianigTotalInterestForLastPayment);
        lease.setPanneltyDuration(duration);
        lease.setIsPaymentOutDated(isPaymentOutDated);
        lease.setCurrentOutStandingBalance(currentOutStandingBalance);
        lease.setNextPaymentDateOutStandingBalance(nextPaymentDateOutStandingBalance);
        lease.setLastPaidInstallmentIndex(lastPaidInstallmentIndex);
        lease.setClosedUserKey(closedUserKey);
        lease.setClosingAmount(closingAmount);
        lease.setClosingInterest(closingInterest);
        lease.setClosingCalculatedInterestAmount(closingCalculatedInterestAmount);
        lease.setClosingAmount(closingCapitalAmount);
        lease.setClosedDateTime(closedDateTime);
        return leasemasterRepository.save(lease);

    }
}
