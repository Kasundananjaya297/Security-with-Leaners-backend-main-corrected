package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.VehicleLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleLocationRepo extends JpaRepository<VehicleLocations, Long>{
    @Query(value = "SELECT id FROM vehicle_locations WHERE vehicleid_fk =:registrationNo",nativeQuery = true)
    Long getIdFromVehicleRegNo(@Param("registrationNo") String registrationNo);

    @Query(value = "SELECT * FROM vehicle_locations WHERE vehicleid_fk =:registrationNo",nativeQuery = true)
    VehicleLocations getVehicleLocationByRegNo(@Param("registrationNo") String registrationNo);
}
