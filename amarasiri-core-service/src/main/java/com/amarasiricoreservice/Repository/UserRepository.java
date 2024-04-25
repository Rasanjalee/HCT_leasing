package com.amarasiricoreservice.Repository;

import com.amarasiricoreservice.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserMaster,Integer> {
    UserMaster findByLognId(String loginId);
}
