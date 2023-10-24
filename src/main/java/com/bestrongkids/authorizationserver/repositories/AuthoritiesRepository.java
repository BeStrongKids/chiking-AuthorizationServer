package com.bestrongkids.authorizationserver.repositories;

import com.bestrongkids.authorizationserver.entities.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
    Optional<Authorities> findAuthoritiesByName(String name);
}
