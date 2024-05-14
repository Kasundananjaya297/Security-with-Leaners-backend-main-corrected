package com.example.SecuritywithLeaners.Repo;



import com.example.SecuritywithLeaners.Entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PackageRepo extends JpaRepository<Package,String> {
    @Query(value = "SELECT MAX(packageID) FROM package", nativeQuery = true)
    String maxPackageID();
    @Query(value = "SELECT COUNT(package_name) FROM package WHERE package_name = :packageName", nativeQuery = true)
    int ExistBypackageName(@Param("packageName") String packageName);
    @Query(value = "SELECT * FROM package WHERE package_name LIKE %:packageName% OR description LIKE %:packageName%", nativeQuery = true)
    List<Package> findByPackageName(@Param("packageName") String packageName);

    @Query(value = "SELECT DISTINCT packageid_packageid FROM package_and_vehicle_type WHERE typeid_typeid IN (:types) AND packageid_packageid NOT IN (SELECT packageid_packageid FROM package_and_vehicle_type WHERE typeid_typeid NOT IN (:types)) LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<String> findPackageByFilter(@Param("types") List<String> types, @Param("pageSize") int pageSize, @Param("offset") int offset);

    @Query(value = "SELECT * FROM package WHERE packageid IN (:packageIDs) " +
            "ORDER BY CASE WHEN :order = 'ASC' THEN package_price END ASC, " +
            "CASE WHEN :order = 'DESC' THEN package_price END DESC",
            nativeQuery = true)
    List<Package> findAllByIdSorted(@Param("packageIDs") List<String> packageIDs, @Param("order") String order);
    //get package by stdID







}