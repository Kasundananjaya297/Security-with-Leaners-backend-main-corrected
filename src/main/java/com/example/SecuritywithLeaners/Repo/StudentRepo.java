package com.example.SecuritywithLeaners.Repo;


import com.example.SecuritywithLeaners.Entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student,String> {
    @Query(value = "SELECT max(stdID) FROM Student_Details " ,nativeQuery = true)
    String getMStdID();

    @Query(value = "SELECT *  FROM student_details WHERE Email = LOWER(:email) OR Telephone = (:telephone) OR NIC = (:nic)", nativeQuery = true)
    Student Available(@Param("email") String email, @Param("telephone") int telephone, @Param("nic") String nic);

    @Query(value = "SELECT * FROM student_details WHERE fname LIKE %:Detail% OR lname LIKE %:Detail%  OR email LIKE %:Detail% OR nic LIKE %:Detail% ", nativeQuery = true)
    List<Student> findByDetail(@Param("Detail") String Detail);


}
