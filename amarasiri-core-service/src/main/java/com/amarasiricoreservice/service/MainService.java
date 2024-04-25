package com.amarasiricoreservice.service;

import com.amarasiricoreservice.DTO.CalculatedLeaseDto;
import com.amarasiricoreservice.DTO.PostLeaseDetailDto;
import com.amarasiricoreservice.entity.LeaseInstallment;
import com.amarasiricoreservice.entity.LeaseMaster;
import com.amarasiricoreservice.entity.LinearEqualInstallmentLastPaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class MainService {
    @Autowired
    LeaseGurantorService leaseGurantorService;

    @Autowired
    LeaseInstallmentService leaseInstallmentService;


    @Autowired
    LeaseDocumentService  leaseDocumentService;

    @Autowired
    LeaseeMasterService leaseeMasterService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    LeasePaymentHistoryService leasePaymentHistoryService;

    public  List<PostLeaseDetailDto> getLeaseDetails(Integer openStatus){
        List<PostLeaseDetailDto> laseDetails = new ArrayList<>();
        leaseeMasterService.getLeasingDetails(openStatus).stream().forEach(
                leaseMaster -> {
                    PostLeaseDetailDto postLeaseDetailDto = new PostLeaseDetailDto();
                    postLeaseDetailDto.setLeaseMaster(leaseMaster);
                    postLeaseDetailDto.setLeasePaymentHistories(leasePaymentHistoryService.getLeasePaymentHistory(leaseMaster.getLeaseKey()));
                    postLeaseDetailDto.setLeaseGurantors(leaseGurantorService.getLeaseGurantors(leaseMaster.getLeaseKey()));
                    postLeaseDetailDto.setLeaseeMaster(leaseeMasterService.getLeaseeDetails(leaseMaster.getLeaseeKey()));
                    postLeaseDetailDto.setVehicle(vehicleService.getVehicles(leaseMaster.getLeaseVehicleKey()));
                    laseDetails.add(postLeaseDetailDto);
                }
        );
        return laseDetails;
    }

    public CalculatedLeaseDto calculateInterest(Integer leaseId, Date paymentReceivedDate) {

        CalculatedLeaseDto calculatedLeaseDto= new CalculatedLeaseDto();
        Double installment = 0.0;
        LeaseMaster lease = leaseeMasterService.getLeaseDetail(leaseId);

        /**
         * lease.getLeaseTypeKey().equals(2) means standard equal ones
         */
        if (lease.getLeaseTypeKey().equals(2)) {

            long timeDifference = paymentReceivedDate.getTime() - lease.getNextPaymentDate().getTime();
            // Convert the time difference to days
            int outDatedDayCount = (int) TimeUnit.MILLISECONDS.toDays(timeDifference) + 1;

            Double penaltyAmount = 0.0;
            LeaseInstallment leaseInstallment = leaseInstallmentService.getMaxLeaseInstallmentForLeaseId(leaseId);

                if(outDatedDayCount>0){
                    penaltyAmount = leaseInstallment.getPrincipal()*0.1*outDatedDayCount;
                    installment = penaltyAmount + lease.getInstallment();
                }
                else {
                    installment = lease.getInstallment();
                }

                calculatedLeaseDto.setInterest(installment-leaseInstallment.getPrincipal());
                calculatedLeaseDto.setInstallment(installment);
                calculatedLeaseDto.setOutDatedDaysCount(outDatedDayCount);
                calculatedLeaseDto.setPenaltyAmount(penaltyAmount);
        }
        /**
         * lease.getLeaseTypeKey().equals(3) means linear equal ones
         */
        else if (lease.getLeaseTypeKey().equals(3)) {

            // Convert Date to LocalDate
            LocalDate paymentDate = Instant.ofEpochMilli(paymentReceivedDate.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LinearEqualInstallmentLastPaymentDetails lastPaymentDetails = leaseInstallmentService.lastPaymentDetails(lease.getLeaseKey());

            int daysDifference = (int)ChronoUnit.DAYS.between(lastPaymentDetails.getLastPaymentDate(), paymentDate);

            if (daysDifference <= 50) {
                installment = lastPaymentDetails.getRemainingCapital() * lastPaymentDetails.getMonthInterestRate() / (30 * 100) * daysDifference;
            } else {
                installment = lastPaymentDetails.getRemainingCapital() * daysDifference * 5 /100;
            }
            calculatedLeaseDto.setOutDatedDaysCount(daysDifference);
            calculatedLeaseDto.setInterest(installment);
            calculatedLeaseDto.setInstallment(1000.0+installment);
            calculatedLeaseDto.setOutDatedDaysCount(0);
            calculatedLeaseDto.setPenaltyAmount(0.0);

        } else {
            calculatedLeaseDto.setOutDatedDaysCount(0);
            calculatedLeaseDto.setInterest(0.0);
            calculatedLeaseDto.setInstallment(0.0);
            calculatedLeaseDto.setOutDatedDaysCount(0);
            calculatedLeaseDto.setPenaltyAmount(0.0);
        }

        return calculatedLeaseDto;







//        leasePaymentHistoryService.savePaymentHistory(leaseId,paymentDate,amount,userId,paymentReceivedDate);
//        LeaseMaster lease = leaseeMasterService.getLeaseDetail(leaseId);
//        LeaseInstallment leaseInstallment=leaseInstallmentService.getMaxLeaseInstallmentForLeaseId(leaseId);


//        CurrentOutStandingBalance

//        remainingLeaseAmmount
//        next payment date
//        is payment outdated
//        current outstanding balance
//        remaining total leasse cost for last payment
//        remaining total interst for lastpayment
//        next payent date outstatding balance
//        remaining capital
//        CLOSing capitalamonut
//        closing clacilated interest
//        closing amount
//        closinginterest
//        lastPaid intalmentindex
//        panelty mount fornextpayment
//        panelty duration for next paymnet
//        remaining capital fortoday
//       return null;
    }

    public LeaseMaster saveLease(PostLeaseDetailDto postLeaseDetailDto) {
        LeaseMaster leaseMaster = leaseeMasterService.saveLeaseeMaster(postLeaseDetailDto);
        if(leaseMaster!=null){
            postLeaseDetailDto.getInstallments().stream().forEach(leaseInstallment -> leaseInstallment.setLeaseId(leaseMaster.getLeaseKey()));
            leaseInstallmentService.saveLeaseInstallments(postLeaseDetailDto.getInstallments());

            postLeaseDetailDto.getLeaseDocuments().stream().forEach(leaseDocuments -> leaseDocuments.setLeaseKey(leaseMaster.getLeaseKey()));
            leaseDocumentService.saveLeaseDocuments(postLeaseDetailDto.getLeaseDocuments());


        }
        return  leaseMaster;
    }

    public LeaseMaster makePayment(Integer leaseId, Double installment, Double interest, Date paymentDate, Integer userId,Integer penaltyDuration,Double penaltyAmount) {
        LeaseMaster lease = leaseeMasterService.getLeaseDetail(leaseId);

        int leaseType = lease.getLeaseTypeKey();
        LeaseMaster updatedLease = new LeaseMaster();
        leasePaymentHistoryService.savePaymentHistory(leaseId,paymentDate,installment,userId,new Date());
        LocalDate nextPaymentDate = Instant.ofEpochMilli(lease.getNextPaymentDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusMonths(1);
        Double remainingCapital = lease.getRemainingCapial()-installment-interest;
        Double remainingLeaseAmount = lease.getRemainingLeaseAmount() - installment-interest;
        Double totalLeaseCost =  lease.getTotalLeaseCost() - installment - interest;
        Double totalInterestCollected =  lease.getTotalInterestCollected()+interest;

        Double remainingTotalLeaseCostForLastPayment = lease.getRemainingTotalLeaseCostForLastPayment() - installment - interest;
        switch (leaseType){
            case 1:
                break;
            case 2:
                LeaseInstallment leaseInstallment = leaseInstallmentService.updatedInstallment(leaseId,lease.getLastPaidInstallmentIndex(),penaltyDuration,penaltyAmount,paymentDate,userId);
                LeaseInstallment lastLeaseInstallment = leaseInstallmentService.getLastLeaseInstallmentForLeaseId(leaseId);

                Double remaianigTotalInterestForLastPayment = lease.getRemainingTotalInterestForLastPayment() - interest;;
                Integer duration =penaltyDuration;
                Integer isPaymentOutDated = duration>0 ? 1:0;
                Double currentOutStandingBalance = lease.getCurrentOutStandingBalance()- installment;
                Double nextPaymentDateOutStandingBalance = lease.getNextPaymentDateOutStandingBalance() - installment;
                Integer lastPaidInstallmentIndex = lastLeaseInstallment.getInstallmentIndex()>=lease.getLastPaidInstallmentIndex()+1?lease.getLastPaidInstallmentIndex()+1:lastLeaseInstallment.getInstallmentIndex();

                Integer closedUserKey;
                Double closingAmount;
                Double closingInterest;
                Double closingCalculatedInterestAmount;
                Double closingCapitalAmount;
                Date closedDateTime;
                if (lastPaidInstallmentIndex.equals(lastLeaseInstallment.getInstallmentIndex())) {
                    closingAmount = installment;
                    closingInterest = interest;
                    closedUserKey = userId;
                    closingCapitalAmount = installment - interest;
                    closingCalculatedInterestAmount = interest;
                    closedDateTime = paymentDate;
                } else {
                    closingAmount = 0.0;
                    closingInterest = 0.0;
                    closedUserKey = null;
                    closingCapitalAmount = 0.0;
                    closingCalculatedInterestAmount = 0.0;
                    closedDateTime = null;
                }
                updatedLease = leaseeMasterService.updatePayment(leaseId,
                        nextPaymentDate,
                        remainingCapital,
                        remainingLeaseAmount,
                        totalLeaseCost,
                        totalInterestCollected,
                        remainingTotalLeaseCostForLastPayment,
                        remaianigTotalInterestForLastPayment,
                        duration,
                        isPaymentOutDated,
                        currentOutStandingBalance,
                        nextPaymentDateOutStandingBalance,
                        lastPaidInstallmentIndex,
                        closedUserKey,
                        closingAmount,
                        closingInterest,
                        closingCalculatedInterestAmount,
                        closingCapitalAmount,
                        closedDateTime
                        );


                break;
            case 3:
                updatedLease = leaseeMasterService.updatePayment(leaseId,nextPaymentDate,remainingCapital,remainingLeaseAmount,totalLeaseCost,totalInterestCollected,0.0,0.0,0,0,0.0,0.0,0,null,0.0,0.0,0.0,0.0,null);

                break;
            default: break;
        }
        return updatedLease;
    }
}
