package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.TrainerDrivingLicence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainerDrivingLicenceRepo extends JpaRepository<TrainerDrivingLicence, Long>{
    @Query(value = "SELECT COUNT(*) FROM trainer_driving_licence WHERE trainerid_fk =(:trainerid_fk) AND updated_or_issued_on =(:updated_or_issued_on)", nativeQuery = true)
    int countByTrainerIdAndUpdatedOrIssuedOn(@Param("trainerid_fk") String trainerid_fk, @Param("updated_or_issued_on") String updated_or_issued_on);

}

