package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepo extends JpaRepository<Vehicle, String> {
    @Modifying
    @Query(value = "update vehicle set url_of_book =:url where registration_no =:regNo", nativeQuery = true)
    void updateUrl(@Param("regNo") String regNo,@Param("url") String url);


}
