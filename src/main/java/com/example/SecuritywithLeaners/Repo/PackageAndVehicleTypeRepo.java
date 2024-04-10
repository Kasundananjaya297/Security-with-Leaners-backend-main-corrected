package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.PackageAndVehicleType;
import com.example.SecuritywithLeaners.Entity.PackageAndVehicleTypeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PackageAndVehicleTypeRepo extends JpaRepository<PackageAndVehicleType, PackageAndVehicleTypeID>{

    @Modifying
    @Query(value = "DELETE FROM package_and_vehicle_type WHERE packageID_packageID = :packageID", nativeQuery = true)
    void deleteAllByPackageID(String packageID);

    @Query(value = "SELECT * FROM package_and_vehicle_type WHERE packageID_packageID = :packageID", nativeQuery = true)
    List<PackageAndVehicleType> findByPackageID(String packageID);
    @Query(value = "SELECT typeid_typeid FROM package_and_vehicle_type WHERE packageID_packageID = :packageID", nativeQuery = true)
    List<String> findTypeByPackageID(String packageID);



}
//private int lessons;
//private int extraLessons;
//private double priceForExtraLesson;
//private int totalLessons;
//private int participatedLessons;
//private int remainingLessons;