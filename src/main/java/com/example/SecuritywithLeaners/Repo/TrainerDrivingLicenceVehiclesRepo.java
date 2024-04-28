package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.TrainerDrivingLicence;
import com.example.SecuritywithLeaners.Entity.TrainerDrivingLicenceVehicles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerDrivingLicenceVehiclesRepo extends JpaRepository<TrainerDrivingLicenceVehicles, Long> {
}
