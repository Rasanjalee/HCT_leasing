package com.amarasiricoreservice.service;

import com.amarasiricoreservice.Repository.LeaseDocumentRepository;
import com.amarasiricoreservice.entity.LeaseDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaseDocumentService {

    @Autowired
    LeaseDocumentRepository leaseDocumentRepository;

    public List<LeaseDocuments> saveLeaseDocuments(List<LeaseDocuments> leaseDocuments){

        return leaseDocumentRepository.saveAll(leaseDocuments);
    }

}
