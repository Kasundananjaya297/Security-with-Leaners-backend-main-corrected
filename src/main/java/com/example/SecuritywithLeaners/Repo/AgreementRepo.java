package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Agreement;
import com.example.SecuritywithLeaners.Entity.AgreementID;
import com.example.SecuritywithLeaners.Entity.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgreementRepo extends JpaRepository<Agreement, AgreementID> {

    @Query(value = "SELECT is_finished FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Boolean agreementIsFinished(@Param("stdID") String stdID);
    @Query(value = "SELECT * FROM agreement WHERE stdid_stdid =:stdID",nativeQuery = true)
    List<Agreement> getAgreementsByStdID(@Param("stdID") String stdID);
    @Query(value = "SELECT package_price FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getPackagePrice(@Param("stdID") String stdID);
    @Modifying
    @Query(value = "UPDATE agreement SET discount = :discount , total_amount = :totalAmount   WHERE stdid_stdid =:stdID AND packageid_packageid = :packageID", nativeQuery = true)
    void updateDiscount(@Param("stdID") String stdID,@Param("discount") double discount,@Param("totalAmount") double totalAmount,@Param("packageID") String packageID);

    @Query(value = "SELECT total_amount FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getTotalAmount(@Param("stdID") String stdID);

    @Modifying
    @Query(value = "UPDATE agreement SET total_amount_to_pay=:totalAmountTopay WHERE stdid_stdid =:stdID AND packageid_packageid = :packageID" ,nativeQuery = true)
    void updateTotalAmountToPay(@Param("stdID") String stdID,@Param("totalAmountTopay") double totalAmountTopay,@Param("packageID") String packageID);

    @Query(value = "SELECT total_amount_to_pay FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getTotalAmountToPay(@Param("stdID") String stdID);

    @Query(value = "SELECT total_amount_paid FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getTotalAmountToPaid(@Param("stdID") String stdID);

    @Query(value = "SELECT discount FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getDiscount(@Param("stdID") String stdID);

    @Query(value = "SELECT  total_amount_for_extra_sessions FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getTotalAmountForExtraSessions(@Param("stdID") String stdID);

    @Modifying
    @Query(value = "UPDATE agreement SET total_amount_for_extra_sessions=:totalAmountForExtraSessions WHERE stdid_stdid =:stdID AND packageid_packageid = :packageID" ,nativeQuery = true)
    void updateTotalAmountForExtraSessions(@Param("stdID") String stdID,@Param("totalAmountForExtraSessions") double totalAmountForExtraSessions,@Param("packageID") String packageID);

    @Modifying
    @Query(value = "UPDATE agreement SET total_amount_paid = :totalAmountPaid WHERE stdid_stdid =:stdID AND packageid_packageid = :packageID",nativeQuery = true)
    void updateTotalAmountPaid(@Param("stdID") String stdID,@Param("totalAmountPaid") double totalAmountPaid,@Param("packageID") String packageID);

    @Query(value = "SELECT packageid_packageid FROM agreement WHERE stdid_stdid = :stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)",nativeQuery = true)
    String PackageID(@Param("stdID") String stdID);

    @Modifying
    @Query(value = "UPDATE agreement SET total_amount_for_extras_not_in_agreement = :totalAmountForExtrasNotInAgreement WHERE stdid_stdid =:stdID AND packageid_packageid = :packageID",nativeQuery = true)
    void updateTotalAmountForExtrasNotInAgreement(@Param("stdID") String stdID,@Param("totalAmountForExtrasNotInAgreement") double totalAmountForExtrasNotInAgreement,@Param("packageID") String packageID);

    @Query(value = "SELECT total_amount_for_extras_not_in_agreement FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Double getTotalAmountForExtrasNotInAgreement(@Param("stdID") String stdID);

    @Query(value = "SELECT * FROM agreement WHERE stdid_stdid =:stdID AND agreement_date =(SELECT MAX(agreement_date) FROM agreement WHERE stdid_stdid=:stdID)", nativeQuery = true)
    Agreement getLatestAgreement(@Param("stdID") String stdID);

}

