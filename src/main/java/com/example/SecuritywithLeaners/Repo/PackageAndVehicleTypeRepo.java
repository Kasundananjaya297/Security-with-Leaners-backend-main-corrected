package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.PackageAndVehicleType;
import com.example.SecuritywithLeaners.Entity.PackageAndVehicleTypeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PackageAndVehicleTypeRepo extends JpaRepository<PackageAndVehicleType, PackageAndVehicleTypeID>{

    @Modifying
    @Query(value = "DELETE FROM package_and_vehicle_type WHERE packageID_packageID = :packageID", nativeQuery = true)
    void deleteAllByPackageID(String packageID);
}
