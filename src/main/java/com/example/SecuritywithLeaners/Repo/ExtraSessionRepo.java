package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Agreement;
import com.example.SecuritywithLeaners.Entity.AgreementID;
import com.example.SecuritywithLeaners.Entity.ExtraSession;
import com.example.SecuritywithLeaners.Entity.ExtraSessionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface ExtraSessionRepo extends JpaRepository<ExtraSession,Integer> {
    @Modifying
    @Query(value = "UPDATE extra_session e SET e.extra_lessons = :extraLessons, e.price_for_extra_lesson = :priceForExtraLesson, " +
            "e.total_lessons = :totalLessons WHERE e.package_and_vehicle_type_typeid_typeid =:typeID AND e.agreement_stdid_stdid = :stdID AND e.agreement_packageid_packageid = :packageID",
            nativeQuery = true)
    void updateExtraSession(@PathVariable("typeID") String typeID, @PathVariable("extraLessons") int extraLessons,
                            @PathVariable("priceForExtraLesson") double priceForExtraLesson, @PathVariable("totalLessons") int totalLessons,
                            @PathVariable("stdID") String stdID, @PathVariable("packageID") String packageID);
    }
