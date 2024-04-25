package com.amarasiricoreservice.service;

import com.amarasiricoreservice.Repository.VehicleRepository;
import com.amarasiricoreservice.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle getVehicles(Integer vehicleId){

            return vehicleRepository.findByLeaseeVehicleKey(vehicleId);
    }
}
