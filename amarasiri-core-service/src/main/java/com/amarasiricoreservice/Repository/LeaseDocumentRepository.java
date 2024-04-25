package com.amarasiricoreservice.Repository;

import com.amarasiricoreservice.entity.LeaseDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaseDocumentRepository extends JpaRepository<LeaseDocuments,Integer> {
}
