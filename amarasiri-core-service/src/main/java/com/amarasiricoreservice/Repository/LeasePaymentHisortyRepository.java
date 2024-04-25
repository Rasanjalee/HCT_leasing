package com.amarasiricoreservice.Repository;

import com.amarasiricoreservice.entity.LeasePaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeasePaymentHisortyRepository extends JpaRepository<LeasePaymentHistory,Integer> {

    List<LeasePaymentHistory> findAllByLeaseKey(int leaseKey);
}
