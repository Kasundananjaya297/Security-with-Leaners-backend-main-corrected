package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.PackageAndVehicleType;
import com.example.SecuritywithLeaners.Entity.PackageAndVehicleTypeID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageAndVehicleTypeRepo extends JpaRepository<PackageAndVehicleType, PackageAndVehicleTypeID>{
}
