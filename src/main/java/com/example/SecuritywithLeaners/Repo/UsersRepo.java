package com.example.SecuritywithLeaners.Repo;

import com.example.SecuritywithLeaners.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, String> {
    Optional<Users> findUsersByUsername(String userName);


}
