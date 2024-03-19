package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.PermitAndVehicleType;
import com.example.SecuritywithLeaners.Entity.PermitAndVehicleTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PermitAndVehicleTypeRepo extends JpaRepository<PermitAndVehicleType, PermitAndVehicleTypeId> {

    @Query(value = "SELECT COUNT(*) FROM permit_and_vehicle_type WHERE serial_no_serial_no = :serialNo", nativeQuery = true)
    int countByAllBySerialNo(@Param("serialNo") String serialNo);


}
