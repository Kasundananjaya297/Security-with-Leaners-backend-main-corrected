package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<Vehicle, String> {


}
