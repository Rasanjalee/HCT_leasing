package com.amarasiricoreservice.Repository;

import com.amarasiricoreservice.entity.LeaseMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeasemasterRepository extends JpaRepository<LeaseMaster,Integer> {


    List<LeaseMaster> findByIsLeaseClosed(Integer isClosed);
}
