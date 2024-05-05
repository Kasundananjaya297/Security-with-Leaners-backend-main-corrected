package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepo extends JpaRepository<Vehicle, String> {
    @Modifying
    @Query(value = "update vehicle set url_of_book =:url where registration_no =:regNo", nativeQuery = true)
    void updateUrl(@Param("regNo") String regNo,@Param("url") String url);

    @Modifying
    @Query(value = "UPDATE vehicle SET vehicle_status =:status where registration_no =:regNo", nativeQuery = true)
    void updateStatus(@Param("regNo") String regNo,@Param("status") String status);

    @Query(value = "SELECT vehicle_status FROM vehicle where registration_no =:regNo",nativeQuery = true )
    String getStatus(@Param("regNo") String regNo);

    @Query(value = "SELECT * FROM vehicle WHERE typeid_typeid =:typeId",nativeQuery = true)
    List<Vehicle> getVehicleByType(@Param("typeId") String typeId);

    @Query(value = "SELECT DISTINCT typeid_typeid FROM vehicle",nativeQuery = true)
    List<String> getAllVehicleTypes();


}
