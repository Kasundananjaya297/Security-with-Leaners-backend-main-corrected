package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.VehicleServicesAndRepair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrRepairRepo extends JpaRepository<VehicleServicesAndRepair, Long> {


}
