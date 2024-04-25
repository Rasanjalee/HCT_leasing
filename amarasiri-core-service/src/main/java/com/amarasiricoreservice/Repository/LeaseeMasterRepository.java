package com.amarasiricoreservice.Repository;

import com.amarasiricoreservice.entity.LeaseeMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseeMasterRepository extends JpaRepository<LeaseeMaster,Integer> {
    LeaseeMaster findByLeaseeId(Integer leaseeId);
}
