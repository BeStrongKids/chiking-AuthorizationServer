package com.bestrongkids.authorizationserver.repositories;

import com.bestrongkids.authorizationserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByName(String name);
    Optional<User> findUserByEmail(String email);

//    @Query("SELECT u.name, a.name FROM User u JOIN u.authority a WHERE u.id = :userId")
//    Optional<Object[]> findUserNameAndAuthorityNameById(@Param("userId") Long userId);

    @Query("SELECT a.name FROM User u JOIN u.authorities am JOIN am.authoritiesId a WHERE u.email = :email")
    List<String> findAuthorityNamesByEmail(@Param("email") String email);
}
