package amarasiricoreservice.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "leaseinstallments")
public class LeaseInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "leaseinstalmentkey")
    private Integer leaseInstallmentId;

    @Column(name = "installmentindex")
    private Integer installmentIndex;

    @Column(name = "leasekey")
    private Integer leaseId;

    @Column(name = "paymentdate")
    private Date paymentDate;

    @Column(name = "beginingbalance")
    private Double beginigBalance;

    @Column(name = "beginingleasecost")
    private Double beginingLeaseCost;

    @Column(name = "endingbalance")
    private Double endingBalance;

    @Column(name = "endinglesebalance")
    private Double endingLeaseBalance;

    @Column(name = "installment")
    private Double installment;

    @Column(name = "principal")
    private Double principal;

    @Column(name = "interst")
    private Double interest;

    @Column(name = "paidamount")
    private Double paidAmount;

    @Column(name = "paidamountbalance")
    private Double paidAmountBalance;

    @Column(name ="totalinterestpayied")
    private Double totalInterestPayied;

    @Column(name ="totaliapitalpayied")
    private Double totalCapitalPayied;

    @Column(name ="paieddatetime")
    private Date paiedDateTime;

    @Column(name ="receiveduserkey")
    private Integer receivedUserKey;

    @Column(name ="remainingprincipalportion")
    private Double remainingPrincipalPortion;

    @Column(name ="remaininginterestportion")
    private Double remainingInterestPortion;

    @Column(name ="currentoutstandingbalance")
    private Double currentOutStandingBalance;

    @Column(name ="penaltyduration")
    private Integer penaltyDuration;

    @Column(name ="penaltyamount")
    private Double penaltyAmount;

    @Column(name ="penaltyinterestrate")
    private Double penaltyInterestRate;

    @Column(name ="modifieduserkey")
    private Double modifiedUserKey;

    @Column(name ="modifieddatetime")
    private Date modifiedDateTime;

    @Column(name ="ispaymentdone")
    private Integer isPaymentDone;

    @Column(name ="islatepayment")
    private Integer isLatePayment;

    @Column(name ="ispartpayment")
    private Integer isPartPayment;

    @Column(name ="ispaymentoutdated")
    private Integer isPaymentOutDated;

    @Column(name ="remarks")
    private String remarks;

    @Column(name ="statuscode")
    private Integer statusCode;

    @Column(name ="numberofinstallments")
    private Integer numberOfInstallments;

    @Column(name ="lastpenaltycalculateddatetime")
    private Date lastPenaltyCalculatedDateTime;




}
