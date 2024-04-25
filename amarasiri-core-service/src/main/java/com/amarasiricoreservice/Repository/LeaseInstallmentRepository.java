package com.amarasiricoreservice.Repository;

import com.amarasiricoreservice.entity.LeaseInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaseInstallmentRepository extends JpaRepository<LeaseInstallment,Integer> {
    @Query("select li from LeaseInstallment li " +
            "where li.leaseId= :leaseId " +
            "and li.isPaymentDone= 0" +
            "and li.installmentIndex= " +
                "(select min(lI.installmentIndex)" +
                "  from LeaseInstallment lI " +
                "where lI.leaseId =: leaseId) ")
    LeaseInstallment getMaxLeaseInstallment(int leaseId);

    LeaseInstallment findByLeaseIdAndInstallmentIndex(Integer leaseId, Integer lastPaidInstallmentIndex);

    @Query("select li from LeaseInstallment li " +
            "where li.leaseId= :leaseId and " +
            " li.installmentIndex= " +
            "      (select min(lI.installmentIndex)" +
            "       from LeaseInstallment lI " +
            "       where lI.leaseId =: leaseId)")
    LeaseInstallment getLastInstallment(Integer leaseId);
}
