package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.ExtrasNotINAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExtrasNotInAgreementRepo extends JpaRepository<ExtrasNotINAgreement,Integer> {
    @Query(value = "SELECT COUNT(*) FROM extras_notinagreement WHERE agreement_packageid_packageid =:packageID AND agreement_stdid_stdid =:stdID AND typeid =:typeID",nativeQuery = true)
    int countExtraSession(@Param("stdID") String stdID,@Param("packageID") String packageID,@Param("typeID") String typeID);

    @Query(value = "SELECT * FROM extras_notinagreement WHERE agreement_packageid_packageid =:packageID AND agreement_stdid_stdid =:stdID",nativeQuery = true)
    List<ExtrasNotINAgreement> getExtraSession(@Param("stdID") String stdID, @Param("packageID") String packageID);

    @Query(value = "SELECT SUM(price_for_extra_lesson) FROM extras_notinagreement WHERE agreement_packageid_packageid =:packageID AND agreement_stdid_stdid =:stdID",nativeQuery = true)
    double getTotalPrice(@Param("stdID") String stdID,@Param("packageID") String packageID);

    @Query(value = "SELECT typeid FROM extras_notinagreement WHERE agreement_packageid_packageid =:packageID AND agreement_stdid_stdid =:stdID",nativeQuery = true)
    List<String> getTypeID(@Param("stdID") String stdID,@Param("packageID") String packageID);
}
