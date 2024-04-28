package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Entity.Trainers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepo extends JpaRepository<Trainers, String> {
    @Query(value = "SELECT max(trainerid) FROM trainers", nativeQuery = true)
    String getMaxTrainerID();

    @Query(value = "SELECT * FROM trainers WHERE email = LOWER(:email) OR telephone = (:telephone) OR nic = (:nic) OR licence_no = (:licenceNo)", nativeQuery = true)
    Trainers availableTrainer(String email, int telephone, String nic,String licenceNo);




}
